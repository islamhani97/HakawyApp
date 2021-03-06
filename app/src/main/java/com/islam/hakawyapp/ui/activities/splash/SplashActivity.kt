package com.islam.hakawyapp.ui.activities.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseUser
import com.islam.hakawyapp.R
import com.islam.hakawyapp.databinding.ActivitySplashBinding
import com.islam.hakawyapp.ui.activities.main.MainActivity
import com.islam.hakawyapp.ui.activities.signin.SignInActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        viewModel.getCurrentUser()
        observeOnData()
    }

    private fun observeOnData() {
        viewModel.currentUserLiveData.observe(this, Observer<FirebaseUser?> { firebaseUser ->

            val intent:Intent = if (firebaseUser == null) {
                Intent(this@SplashActivity, SignInActivity::class.java)
            } else {
                Intent(this@SplashActivity, MainActivity::class.java)
            }
            startActivity(intent)
            finish()
        })
    }

//    private fun observeOnDataf() {
//        viewModel.currentUserLiveData.observe(
//            this,
//            Observer<FirebaseUser?> { firebaseUser ->
//                if (firebaseUser == null) {
//                    viewModel.setSignInStatus(false)
//                    val intent = Intent(this@SplashActivity, SignInActivity::class.java)
//                    intent.putExtra("Completable", false)
//                    startActivity(intent)
//                    finish()
//                } else {
//                    viewModel.getSignInStatus()
//                }
//            })
//        viewModel.signInStatusLiveData.observe(
//            this,
//            Observer<Boolean?> { signedIn ->
//                if (!signedIn!!) {
//                    val intent = Intent(this@SplashActivity, SignInActivity::class.java)
//                    intent.putExtra("Completable", true)
//                    startActivity(intent)
//                } else {
//                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
//                }
//                finish()
//            })
//    }
}