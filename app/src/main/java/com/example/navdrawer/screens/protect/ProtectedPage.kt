package com.example.navdrawer.screens.protect

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.navdrawer.viewModel.AppViewModel
import com.example.navdrawer.service.UserService
import com.example.navdrawer.viewModel.UserViewModel


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
@Preview(showBackground = true)
fun TestProtectedPage(appviewModel: AppViewModel = AppViewModel(Application())) {


    val userViewModel = UserViewModel(UserService.instance)

    var hasToken by remember { mutableStateOf(appviewModel.getToken().isNotEmpty()) }

    var token by remember {
        mutableStateOf(appviewModel.getToken())
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text("Query protected route", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)

Row {
    Button(onClick = {


       userViewModel.testProtectedRequest(token)

    }) {
        Text(text = "Request Protected")
    }

    Spacer(modifier = Modifier.width(16.dp))

    Button(onClick = {


        appviewModel.deleteToken()
        token = ""
        hasToken = false



    }) {
        Text(text = "Eliminar Token")
    }

}


        Text(text = "Has token?: $hasToken")

        Text("${token}")

    }
}



