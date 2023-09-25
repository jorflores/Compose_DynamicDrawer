package com.example.navdrawer.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.example.navdrawer.viewModel.AppViewModel

@Composable
fun HomePage(appViewModel: AppViewModel, navController: NavHostController) {


    /*val loggedIn = remember {
        mutableStateOf(viewModel.isLoggedIn.value)
    }*/



    LaunchedEffect(Unit) {
        if (appViewModel.isUserLoggedIn() && !appViewModel.isPrivacySigned()) {
            navController.navigate("Privacy")
        }
    }

    Column {
        Text(text = "Welcome to HomePage")
        //Text(text = "Is Logged In: ${viewModel.isUserLoggedIn()}")
    }
}