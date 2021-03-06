package com.islam.hakawyapp.ui.activities.signin

import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.islam.hakawyapp.R
import com.islam.hakawyapp.callbacks.SignInCallback
import com.islam.hakawyapp.SignInNavGraphDirections
import com.islam.hakawyapp.databinding.ActivitySignInBinding
import com.islam.hakawyapp.pojo.User
import com.islam.hakawyapp.receivers.InternetReceiver
import com.islam.hakawyapp.ui.activities.main.MainActivity
import com.islam.hakawyapp.ui.dialogs.LoadingDialog
import com.islam.hakawyapp.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : AppCompatActivity(), SignInCallback {

    private lateinit var binding: ActivitySignInBinding
    private val viewModel: SignInViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var loading: LoadingDialog
    private var verificationId: String? = null
//    private val internetReceiver: InternetReceiver = InternetReceiver(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        loading = LoadingDialog(this)
        navController =
            (supportFragmentManager.findFragmentById(R.id.activity_sign_in_host) as NavHostFragment).navController
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
//        registerReceiver(internetReceiver, intentFilter)
        observeOnData()
    }

    override fun onDestroy() {
        super.onDestroy()
//        unregisterReceiver(internetReceiver)
        loading.dismiss()
    }

    private fun observeOnData() {
        viewModel.verificationIdLiveData.observe(this,
            Observer<String> { s ->
                verificationId = s
                authCodeDestination()
                loading.dismiss()
            })

        viewModel.credentialLiveData.observe(this,
            Observer<PhoneAuthCredential?> { credential ->
                loading.show()
                viewModel.signInWithPhoneAuthCredential(credential)
            })

        viewModel.signInResultLiveData.observe(this,
            Observer<Void?> { viewModel.getUser() })

        viewModel.userLiveData.observe(this,
            Observer<User?> { user ->
                profileInfoDestination(user.name)
                loading.dismiss()
            })

        viewModel.postUserResultLiveData.observe(this, Observer<Void?> {
            loading.dismiss()
            startActivity(Intent(this@SignInActivity, MainActivity::class.java))
            viewModel.setSignInStatus(true)
            finish()
        })

        viewModel.errorLiveData.observe(this, Observer<Int?> { errorCode ->
            loading.dismiss()

            when (errorCode) {
                ERROR_CODE_NO_INTERNET -> {
                    Toast.makeText(
                        this@SignInActivity,
                        R.string.error_no_internet,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                ERROR_CODE_NETWORK_CONNECTION -> {
                    Toast.makeText(
                        this@SignInActivity, R.string.error_network, Toast.LENGTH_SHORT
                    ).show()
                }
                ERROR_CODE_UNKNOWN -> {
                    Toast.makeText(this@SignInActivity, R.string.error_unknown, Toast.LENGTH_SHORT)
                        .show()
                }
                ERROR_CODE_SERVER -> {
                    Toast.makeText(this@SignInActivity, R.string.error_server, Toast.LENGTH_SHORT)
                        .show()
                }
                ERROR_CODE_PHONE_NUMBER -> {
                    Toast.makeText(
                        this@SignInActivity,
                        R.string.error_incorrect_phone_number,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                ERROR_CODE_VERIFICATION_CODE -> {
                    Toast.makeText(
                        this@SignInActivity,
                        R.string.error_invalid_auth_code,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                ERROR_CODE_USER_NO_INTERNET -> {
                    showDialog(R.string.error_no_internet,
                        R.string.retry,
                        DialogInterface.OnClickListener { dialog, which ->
                            loading.show()
                            viewModel.getUser()
                        })
                }
                ERROR_CODE_USER_NETWORK_CONNECTION -> {
                    showDialog(R.string.error_network,
                        R.string.retry,
                        DialogInterface.OnClickListener { dialog, which ->
                            loading.show()
                            viewModel.getUser()
                        })
                }
                ERROR_CODE_USER_UNKNOWN -> {
                    showDialog(R.string.error_unknown,
                        R.string.retry,
                        DialogInterface.OnClickListener { dialog, which ->
                            loading.show()
                            viewModel.getUser()
                        })
                }
            }


        })
    }

    override fun agree() {
        val action = SignInNavGraphDirections.actionGlobalPhoneFragment()
        navController.navigate(action)
    }

    override fun verifyPhoneNumber(phoneNumber: String) {
        loading.show()
        viewModel.sendVerificationCode(phoneNumber, this);
    }

    override fun onCodeSubmit(code: String) {
        loading.show();
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code);
        viewModel.signInWithPhoneAuthCredential(credential);
    }

    override fun submitProfileInfo(user: User) {
        loading.show();
        viewModel.postUser(user);
    }


    private fun authCodeDestination() {

//        if (navController.currentDestination!!.id != R.id.authCodeFragment &&
//            navController.currentDestination().getId() != R.id.userInfoFragment) {
//            NavDirections action = SignInNavGraphDirections.actionGlobalAuthCodeFragment();
//            navController.navigate(action);
//        }


        val action = SignInNavGraphDirections.actionGlobalAuthCodeFragment()
        navController.navigate(action)

    }

    private fun profileInfoDestination(username: String?) {
//        if (navController.getCurrentDestination().getId() != R.id.userInfoFragment) {
//            NavDirections action = SignInNavGraphDirections . actionGlobalUserInfoFragment (username);
//            navController.navigate(action);
//        }

        val action = SignInNavGraphDirections.actionGlobalProfileInfoFragment(username)
        navController.navigate(action)

    }

    private fun showDialog(
        message: Int,
        actionText: Int,
        listener: DialogInterface.OnClickListener
    ) {
        MaterialAlertDialogBuilder(this@SignInActivity, R.style.DialogTheme)
            .setMessage(message)
            .setPositiveButton(actionText, listener)
            .setCancelable(false)
            .setBackground(getDrawable(R.drawable.shape_dialog_background))
            .show()
    }


}