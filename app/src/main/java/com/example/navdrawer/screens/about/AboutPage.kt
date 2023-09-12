package com.example.navdrawer.screens.about

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.navdrawer.viewModel.AppViewModel

@Composable
fun AboutPage(appviewModel: AppViewModel) {

    Column {
        Text(text = "Welcome to AboutPage")
    }
}