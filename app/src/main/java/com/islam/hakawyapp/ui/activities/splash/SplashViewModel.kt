package com.islam.hakawyapp.ui.activities.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.islam.hakawyapp.data.Repository
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(private val repository: Repository) : ViewModel() {


    val currentUserLiveData: MutableLiveData<FirebaseUser> = MutableLiveData()
//    val signInStatusLiveData: MutableLiveData<Boolean> = MutableLiveData()


    fun getCurrentUser() {
        currentUserLiveData.value = repository.getCurrentUser()
    }


}