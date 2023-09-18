package com.example.navdrawer.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navdrawer.model.OrgRegister
import com.example.navdrawer.model.OrgRegisterResponse
import com.example.navdrawer.model.UserLogin
import com.example.navdrawer.model.UserLoginResponse
import com.example.navdrawer.model.UserProtectedResponse
import com.example.navdrawer.model.UserRegister
import com.example.navdrawer.model.UserRegistrationResponse
import com.example.navdrawer.service.OrgService
import com.example.navdrawer.service.UserService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class OrgViewModel(private val orgService: OrgService) : ViewModel() {


    private val _orgRegisterResult = MutableStateFlow<OrgRegisterResponse?>(null)
    val orgRegisterResult: StateFlow<OrgRegisterResponse?>
        get() = _orgRegisterResult


    fun addOrganization(token: String, org: OrgRegister) {

        viewModelScope.launch {

            var response: OrgRegisterResponse? = null

            try {

                response = orgService.addOrg(token, org)
                _orgRegisterResult.value = response
                response.message?.let { Log.d("RESPONSE", it) }

            } catch (e: Exception) {
                Log.d("RESPONSE", e.localizedMessage)
            }
        }

    }


}
