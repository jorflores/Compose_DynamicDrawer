package com.example.navdrawer.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.example.navdrawer.viewModel.AppViewModel

@Composable
fun SettingsPage(
    appViewModel: AppViewModel,
    navController: NavHostController,
    onLoggedInChanged: (Boolean) -> Unit
) {

    Column {
        Text(text = "Welcome to SettingsPage")
        Button(onClick = {
            appViewModel.setLoggedIn(true)
            appViewModel.setLoggedIn(true)
            onLoggedInChanged(true) // Notify the callback of the change
        }) {
            Text("Log In")
        }
        Button(onClick = {
            appViewModel.setLoggedOut()
            appViewModel.setLoggedIn(false)
            onLoggedInChanged(false) // Notify the callback of the change
        }) {
            Text("Log Out")
        }

        Text(text = "Is Admin?:  ${appViewModel.isAdmin()}")
    }
}


