package com.example.navdrawer.model

data class UserLoginResponse(
    val token: String? = "",
    var message: String? = null,
    val isAdmin: Boolean = false
)