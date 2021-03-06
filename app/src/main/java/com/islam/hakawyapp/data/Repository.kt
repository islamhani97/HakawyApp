package com.islam.hakawyapp.data

import android.app.Activity
import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.firestore.DocumentSnapshot
import com.islam.hakawyapp.pojo.User
import com.islam.hakawyapp.utils.COLLECTION_USERS
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val appStorage: AppStorage,
    private val firebase: Firebase
) {

    fun getCurrentUser(): FirebaseUser? {
        return firebase.getCurrentUser()
    }

    fun sendVerificationCode(
        phoneNumber: String,
        activity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        firebase.sendVerificationCode(phoneNumber,activity, callbacks)
    }

    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential): Task<AuthResult> {
        return firebase.signInWithPhoneAuthCredential(credential)
    }

    fun getUser(): Task<DocumentSnapshot> {
        return firebase.getUser()
    }

    fun postUser(user: User): Task<Void> {
        return firebase.postUser(user)
    }

}