package com.islam.hakawyapp.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.islam.hakawyapp.utils.SH_PREFS_NAME
import com.islam.hakawyapp.utils.SH_PREFS_SIGN_IN_STATUS
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppStorage @Inject constructor( private val sharedPreferences: SharedPreferences,
                                      private val gson: Gson ) {


    //    public void setUser(User user) {
    //        sharedPreferences.edit().putString(Constants.SH_PREFS_USER, gson.toJson(user)).apply();
    //    }
    //
    //    public User getUser() {
    //        String user = sharedPreferences.getString(Constants.SH_PREFS_USER, Constants.NONE);
    //        if (!user.equals(Constants.NONE)) {
    //            return gson.fromJson(user, User.class);
    //        } else {
    //            return null;
    //        }
    //
    //    }



//    fun setSignInStatus(signInStatus: Boolean) {
//        sharedPreferences.edit().putBoolean(SH_PREFS_SIGN_IN_STATUS, signInStatus).apply()
//    }
//
//    fun isSignedIn(): Boolean {
//        return sharedPreferences.getBoolean(
//            SH_PREFS_SIGN_IN_STATUS,
//            false
//        )
//    }



}