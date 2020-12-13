package com.group5.clothing_advisor.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

private const val TAG = "ProfileViewModel"

class ProfileViewModel : ViewModel() {
    val _userName = MutableLiveData<String?>(null)
    val userName: LiveData<String?> get() = _userName

    init {
        val user = FirebaseAuth.getInstance().currentUser
        if(user == null){
            Log.wtf(TAG, "The user is not authenticated, he should not have access to profile!")
        }
        else {
            _userName.postValue(user.displayName)
        }
    }
}