package com.example.navdrawer.screens.login

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.navdrawer.AppViewModel
import com.example.navdrawer.dataStore.DataStoreManager
import com.example.navdrawer.model.UserLoginResponse
import com.example.navdrawer.service.UserService
import com.example.navdrawer.viewModel.UserViewModel
import kotlinx.coroutines.launch


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun LoginPage(appviewModel: AppViewModel) {



    val viewModel = UserViewModel(UserService.instance)

    var telefono by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }


    var loginResult by remember {
        mutableStateOf(UserLoginResponse())
    }

   LaunchedEffect(key1 = viewModel) {
        viewModel.loginResult.collect { result ->
            if (result != null) {
                loginResult = result

                loginResult.token?.let { appviewModel.dataStore.saveToken(it)


                    Log.d("DATASTORE","Token saved: ${it}")}

            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text("Login", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)

        TextField(value = telefono, onValueChange = {
            telefono = it
        }, placeholder = {
            Text("Teléfono de contacto")
        })

        TextField(
            value = password,
            onValueChange = {
                password = it
            },
            placeholder = {
                Text("Contraseña")
            },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
        )

        Button(onClick = {

          viewModel.loginUser(telefono.trim().toInt(),password)




        }) {
            Text(text = "Ingresar")
        }

            Text("${loginResult.token}  ${loginResult.message}")


    }
}



