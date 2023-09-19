package com.example.navdrawer.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.navdrawer.viewModel.AppViewModel

@Composable
fun HomePage(viewModel: AppViewModel) {


    /*val loggedIn = remember {
        mutableStateOf(viewModel.isLoggedIn.value)
    }*/

    Column {
        Text(text = "Welcome to HomePage")
        //Text(text = "Is Logged In: ${viewModel.isUserLoggedIn()}")
    }
}