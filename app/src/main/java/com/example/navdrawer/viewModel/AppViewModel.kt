package com.example.navdrawer.viewModel


import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navdrawer.dataStore.DataStoreManager
import com.example.navdrawer.util.constants.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppViewModel(context: Context) : ViewModel() {

    val dataStore = DataStoreManager(context)

    private var token = ""
    private var isLoggedIn = false
    private var isAdmin = false


    private val _isInitialized = MutableStateFlow(false)
    val isInitialized: StateFlow<Boolean>
        get() = _isInitialized


    init {
        viewModelScope.launch {
            val hasTokenResult = dataStore.hasKeyWithValue(Constants.TOKEN)
            val token = dataStore.getValueFromDataStore(Constants.TOKEN, "")
            val isAdmin = dataStore.getValueFromDataStore(Constants.ISADMIN, false)

            if (hasTokenResult) {
                setLoggedIn()
                setToken(token)
                setIsAdmin(isAdmin)
            }
            _isInitialized.value = true
            Log.d("POSTVALUE","posting value *** ${_isInitialized.value}")

        }
    }

     fun <T> storeValueInDataStore(value: T, key: Preferences.Key<T>) {
        viewModelScope.launch {
            dataStore.storeValue(value, key)
        }
    }


    fun deleteToken(){
        viewModelScope.launch {
            dataStore.deleteValue(Constants.TOKEN)
            token =""
            setLoggedOut()
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

    fun isAdmin(): Boolean {
        return isAdmin
    }

    fun setIsAdmin(value: Boolean){
        isAdmin = value
    }
}