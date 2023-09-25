package com.example.navdrawer.screens.about

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.example.navdrawer.viewModel.AppViewModel

@Composable
fun AboutPage(appViewModel: AppViewModel, navController: NavHostController) {


    LaunchedEffect(Unit) {
        if (!appViewModel.isPrivacySigned()) {
            navController.navigate("Privacy")
        }
    }

    Column {
        Text(text = "Welcome to AboutPage")
    }
}