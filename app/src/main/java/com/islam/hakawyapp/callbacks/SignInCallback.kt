package com.islam.hakawyapp.callbacks

import com.islam.hakawyapp.pojo.User

interface SignInCallback {

    fun agree()

    fun verifyPhoneNumber(phoneNumber: String)

    fun onCodeSubmit(code: String)

    fun submitProfileInfo(user: User)
}