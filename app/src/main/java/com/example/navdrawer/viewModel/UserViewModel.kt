package com.example.navdrawer.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.navdrawer.model.UserLogin
import com.example.navdrawer.model.UserLoginResponse
import com.example.navdrawer.model.UserProtectedResponse
import com.example.navdrawer.model.UserRegister
import com.example.navdrawer.model.UserRegistrationResponse
import com.example.navdrawer.service.UserService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class UserViewModel(private val userService: UserService) : ViewModel() {

    sealed class ApiResult {
        data class Success(val message: String) : ApiResult()
        data class Error(val errorMessage: String) : ApiResult()
    }


    private val _registrationResult = MutableStateFlow<ApiResult?>(null)
    val registrationResult: StateFlow<ApiResult?> = _registrationResult

    private val _loginResult = MutableStateFlow<UserLoginResponse?>(null)
    val loginResult: StateFlow<UserLoginResponse?> //= _loginResult
        get() = _loginResult


    private val _protectedResult = MutableStateFlow<UserProtectedResponse?>(null)
    val protectedResult: StateFlow<UserProtectedResponse?> //= _loginResult
        get() = _protectedResult




    fun addUser(telephone: Int, password: String) {
        val user = UserRegister(telephone, password)

        viewModelScope.launch {
            var response: UserRegistrationResponse? = null
            try {
                response = userService.insertUser(user)
                _registrationResult.value = ApiResult.Success(response.message)
            } catch (e: Exception) {
                e.message?.let {
                    if (response != null) {
                        _registrationResult.value = ApiResult.Error(it)
                    }

                }
            }
        }
    }


    fun loginUser(telephone: Int, password: String) {
        val user = UserLogin(telephone, password)

        viewModelScope.launch {
            var response: UserLoginResponse? = null
            try {
                response = userService.loginUser(user)
                _loginResult.value = response
                Log.d("RESPONSE", response.token.toString())
            } catch (e: HttpException) {

                when (e.code()) {

                    401 -> {
                        Log.d("RESPONSE", e.localizedMessage)
                        val errorMessage = "Invalid credentials"
                        val errorResponse = UserLoginResponse(null, errorMessage)
                        _loginResult.value = errorResponse
                    }

                    else -> {
                        Log.d("RESPONSE", e.localizedMessage)
                        val errorMessage = e.localizedMessage
                        val errorResponse = UserLoginResponse(null, errorMessage)
                        _loginResult.value = errorResponse
                    }
                }
            }
            catch (e: Exception){
                Log.d("RESPONSE", e.localizedMessage)
                val errorMessage = e.localizedMessage
                val errorResponse = UserLoginResponse(null, errorMessage)
                _loginResult.value = errorResponse
            }

        }
    }






    fun testProtectedRequest(token : String) {


        viewModelScope.launch {
            var response: UserProtectedResponse? = null
            try {
                response = userService.protectedRoute(token)
                _protectedResult.value = response
                Log.d("RESPONSE", response.message)
            } catch (e: HttpException) {

                when (e.code()) {

                    401 -> {
                        Log.d("RESPONSE", e.localizedMessage)
                        val errorMessage = "Invalid credentials"
                        val errorResponse = UserProtectedResponse(errorMessage)
                        _protectedResult.value = errorResponse
                    }

                    else -> {
                        Log.d("RESPONSE", e.localizedMessage)
                        val errorMessage = e.localizedMessage
                        val errorResponse = UserProtectedResponse(errorMessage)
                        _protectedResult.value = errorResponse
                    }

                }


            }

        }
    }









}


class AppViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            return AppViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}