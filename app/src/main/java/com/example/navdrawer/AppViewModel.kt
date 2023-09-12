package com.example.navdrawer



import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.navdrawer.dataStore.DataStoreManager

class AppViewModel(context: Context) : ViewModel() {


    val dataStore = DataStoreManager(context)

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




}




