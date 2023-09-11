package com.example.navdrawer.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navdrawer.model.User
import com.example.navdrawer.model.UserResponse
import com.example.navdrawer.service.UserService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val userService: UserService) : ViewModel() {

    sealed class RegistrationResult {
        data class Success(val message: String) : RegistrationResult()
        data class Error(val errorMessage: String) : RegistrationResult()
    }

    private val _registrationResult = MutableStateFlow<RegistrationResult?>(null)
    val registrationResult: StateFlow<RegistrationResult?> = _registrationResult


    fun addUser(telephone: Int, password: String) {
        val user = User(telephone, password)

        viewModelScope.launch {
            var response: UserResponse? = null
            try {
                 response = userService.insertUser(user)
                _registrationResult.value = RegistrationResult.Success(response.message)
            } catch (e: Exception) {
                e.message?.let {
                    if (response != null) {
                        _registrationResult.value = RegistrationResult.Error(it)
                    }

                }
            }
        }
    }
}