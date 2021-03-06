package com.islam.hakawyapp.data

import android.app.Activity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.islam.hakawyapp.pojo.User
import com.islam.hakawyapp.utils.COLLECTION_USERS
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Firebase @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) {

    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }


    fun sendVerificationCode(
        phoneNumber: String,
        activity: Activity,
        callbacks: OnVerificationStateChangedCallbacks
    ) {
        val options = PhoneAuthOptions.newBuilder()
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }


    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential): Task<AuthResult> {
        return firebaseAuth.signInWithCredential(credential)
    }


    fun getUser(): Task<DocumentSnapshot> {
        val userId = firebaseAuth.uid
        return firebaseFirestore.collection(COLLECTION_USERS)
            .document(userId!!).get()
    }

    fun postUser(user: User): Task<Void> {
        val userId = firebaseAuth.uid
        user.id=userId
        return firebaseFirestore.collection(COLLECTION_USERS)
            .document(userId!!).set(user)
    }

}