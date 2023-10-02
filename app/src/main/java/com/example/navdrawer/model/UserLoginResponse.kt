package com.example.navdrawer.model

data class UserLoginResponse(
    val token: String? = "",
    val message: String? = null,
    val isAdmin: Boolean = false
)