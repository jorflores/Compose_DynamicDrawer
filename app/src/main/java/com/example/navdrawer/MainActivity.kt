package com.example.navdrawer

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.navdrawer.navigation.MainPage
import com.example.navdrawer.ui.theme.NavDrawerTheme
import com.example.navdrawer.util.constants.Constants
import com.example.navdrawer.viewModel.AppViewModel
import com.example.navdrawer.viewModel.AppViewModelFactory
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val appViewModel: AppViewModel = viewModel(factory = AppViewModelFactory(context))

            var configLoaded by remember {
                mutableStateOf(false)
            }

            LaunchedEffect(appViewModel.isUserLoggedIn()) {
                delay(500)
                appViewModel.isInitialized.collect { result ->
                    configLoaded = result
                }
            }

            NavDrawerTheme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    if (configLoaded) {
                        MainPage(appViewModel)
                    } else {
                            // Show a loading indicator or splash screen
                            /*CircularProgressIndicator(
                                modifier = Modifier
                                    .size(50.dp),
                                color = Color.Blue,
                                strokeWidth = 8.dp
                            )*/
                        Text(text = "Loading...")
                    }
                }
            }
        }
    }
}






