package com.group5.clothing_advisor.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

private const val TAG = "ProfileViewModel"

class ProfileViewModel : ViewModel() {
    private val _userName = MutableLiveData<String?>("<Unauthorized>")
    val userName: LiveData<String?> get() = _userName

    private val _isVerified = MutableLiveData(true)
    val isVerified: LiveData<Boolean> get() = _isVerified

    private lateinit var currUser: FirebaseUser

    init {
        val user = FirebaseAuth.getInstance().currentUser
        if(user == null){
            Log.wtf(TAG, "The user is not authenticated, he should not have access to profile!")
        }
        else {
            currUser = user
            _userName.postValue(user.displayName)
        }
    }

    fun sendVerificationLink() {
        currUser.sendEmailVerification()
    }
}