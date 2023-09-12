package com.example.navdrawer.viewModel


import android.content.Context
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navdrawer.dataStore.DataStoreManager
import com.example.navdrawer.model.UserLoginResponse
import com.example.navdrawer.util.constants.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppViewModel(context: Context) : ViewModel() {

    val dataStore = DataStoreManager(context)
    private var token = ""
    private var isLoggedIn = false


    private val _isInitialized = MutableStateFlow(false)
    val isInitialized: StateFlow<Boolean>
        get() = _isInitialized


    init {
        viewModelScope.launch {
            var hasTokenResult = dataStore.hasKeyWithValue(Constants.TOKEN)
            var token = dataStore.getValueFromDataStore(Constants.TOKEN, "")

            if (hasTokenResult) {
                setLoggedIn()
                setToken(token)
            }
            _isInitialized.value = true
            Log.d("POSTVALUE","posting value *** ${_isInitialized.value}")

        }
    }

    fun storeAndLoadToken(mytoken: String) {

        viewModelScope.launch {
            dataStore.storeValue(mytoken, Constants.TOKEN)
            token = mytoken
            setLoggedIn()
        }

    }

    fun getToken(): String {
        return token
    }

    fun setToken(mytoken: String) {
        token = mytoken
    }

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