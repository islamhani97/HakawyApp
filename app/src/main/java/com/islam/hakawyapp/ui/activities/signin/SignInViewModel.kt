package com.islam.hakawyapp.ui.activities.signin

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.firestore.DocumentSnapshot
import com.islam.hakawyapp.R
import com.islam.hakawyapp.data.Repository
import com.islam.hakawyapp.pojo.User
import com.islam.hakawyapp.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel
@Inject
constructor(private val repository: Repository, application: Application) :
    AndroidViewModel(application) {

    // Live Data
    val verificationIdLiveData: MutableLiveData<String> = MutableLiveData()
    val credentialLiveData: MutableLiveData<PhoneAuthCredential> = MutableLiveData()
    val signInResultLiveData: MutableLiveData<Void?> = MutableLiveData()
    val postUserResultLiveData: MutableLiveData<Void?> = MutableLiveData()
    val userLiveData: MutableLiveData<User?> = MutableLiveData()
    val errorLiveData: SingleLiveData<Int?> = SingleLiveData()


    // Requests
    fun sendVerificationCode(phoneNumber: String, activity: Activity) {

        if (isInternetConnected(getApplication())) {

            repository.sendVerificationCode(
                phoneNumber,
                activity,
                object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        credentialLiveData.value = credential
                    }

                    override fun onVerificationFailed(e: FirebaseException) {
                        when (e) {
                            is FirebaseAuthInvalidCredentialsException -> {
                                // Invalid request
                                errorLiveData.setValue(ERROR_CODE_PHONE_NUMBER)
                            }
                            is FirebaseTooManyRequestsException -> {
                                // The SMS quota for the project has been exceeded
                                errorLiveData.setValue(ERROR_CODE_SERVER)
                            }
                            is FirebaseNetworkException -> {
                                errorLiveData.setValue(ERROR_CODE_NETWORK_CONNECTION)
                            }
                            else -> {
                                errorLiveData.setValue(ERROR_CODE_UNKNOWN)
                            }
                        }
                    }

                    override fun onCodeSent(verificationId: String, token: ForceResendingToken) {
                        verificationIdLiveData.value = verificationId
                    }
                })

        } else {
            errorLiveData.setValue(ERROR_CODE_NO_INTERNET)
        }
    }

    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {

        if (isInternetConnected(getApplication())) {
            repository.signInWithPhoneAuthCredential(credential)
                .addOnSuccessListener(OnSuccessListener<AuthResult?> {
                    signInResultLiveData.value = null
                }).addOnFailureListener(
                    OnFailureListener { e ->

                        when (e) {
                            is FirebaseAuthInvalidCredentialsException -> {
                                errorLiveData.setValue(ERROR_CODE_VERIFICATION_CODE)
                            }
                            is FirebaseNetworkException -> {
                                errorLiveData.setValue(ERROR_CODE_NETWORK_CONNECTION)
                            }
                            else -> {
                                errorLiveData.setValue(ERROR_CODE_UNKNOWN)
                            }
                        }
                    })

        } else {
            errorLiveData.setValue(ERROR_CODE_NO_INTERNET)
        }
    }

    fun postUser(user: User) {

        if (isInternetConnected(getApplication())) {
            repository.postUser(user)
                .addOnSuccessListener(OnSuccessListener<Void?> {
                    postUserResultLiveData.value = null
                })
                .addOnFailureListener(
                    OnFailureListener { e ->
                        if (e is FirebaseNetworkException) {
                            errorLiveData.setValue(ERROR_CODE_NETWORK_CONNECTION)
                        } else {
                            errorLiveData.setValue(ERROR_CODE_UNKNOWN)
                        }
                    })
        } else {
            errorLiveData.setValue(ERROR_CODE_NO_INTERNET)
        }



    }

    fun getUser() {

        if (isInternetConnected(getApplication())) {

            repository.getUser()
                .addOnSuccessListener(OnSuccessListener<DocumentSnapshot> { documentSnapshot ->

                    val user: User? = if (documentSnapshot.exists()) {
                        documentSnapshot.toObject(User::class.java)
                    } else {
                        User()
                    }
                    userLiveData.value = user
                }).addOnFailureListener(OnFailureListener { e ->

                    if (e is FirebaseNetworkException) {
                        errorLiveData.setValue(ERROR_CODE_USER_NETWORK_CONNECTION)
                    } else {
                        errorLiveData.setValue(ERROR_CODE_USER_UNKNOWN)
                    }

                })

        } else {
            errorLiveData.setValue(ERROR_CODE_USER_NO_INTERNET)
        }


    }

    fun setSignInStatus(signInStatus: Boolean) {
//        repository.setSignInStatus(signInStatus)
    }

}