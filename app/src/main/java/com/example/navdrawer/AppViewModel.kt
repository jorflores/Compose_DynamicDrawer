package com.example.navdrawer


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppViewModel : ViewModel() {

    private var isLoggedIn = false

    fun setLoggedIn() {
        isLoggedIn = true
    }

    fun setLoggedOut() {
        isLoggedIn = false
    }

    fun isUserLoggedIn(): Boolean {
        return isLoggedIn
    }




    /*
    private val _isLoggedIn = MutableLiveData<Boolean>(false)
    val isLoggedIn: LiveData<Boolean>
        get() = _isLoggedIn

    fun setLoggedIn() {
        _isLoggedIn.value = true
    }

    fun setLoggedOut() {
        _isLoggedIn.value = false
    }*/


}