package com.example.navdrawer.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.example.navdrawer.AppViewModel

@Composable
fun SettingsPage(
    viewModel: AppViewModel,
    navController: NavHostController,
    onLoggedInChanged: (Boolean) -> Unit
) {
    var loggedIn by remember {
        mutableStateOf(viewModel.isUserLoggedIn())
    }

    Column {
        Text(text = "Welcome to SettingsPage")
        Button(onClick = {
            viewModel.setLoggedIn()
            loggedIn = true
            onLoggedInChanged(true) // Notify the callback of the change
        }) {
            Text("Log In")
        }
        Button(onClick = {
            viewModel.setLoggedOut()
            loggedIn = false
            onLoggedInChanged(false) // Notify the callback of the change
        }) {
            Text("Log Out")
        }

        Text(text = "Is logged In: ${loggedIn}")
    }
}


