package com.example.navdrawer.screens.register

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navdrawer.service.UserService
import com.example.navdrawer.viewModel.AppViewModel
import com.example.navdrawer.viewModel.UserViewModel



@Preview(showBackground = true)
@Composable
fun RegisterPage(appViewModel: AppViewModel = AppViewModel(LocalContext.current)) {

    val viewModel = UserViewModel(UserService.instance)

    var telefono by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var validarpassword by remember {
        mutableStateOf("")
    }

    var registrationResult by remember { mutableStateOf<UserViewModel.ApiResult?>(null) }

    LaunchedEffect(key1 = viewModel) {
        viewModel.registrationResult.collect { result ->
            registrationResult = result
        }
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text("Registrar Usuario", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)

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

        TextField(value = validarpassword, onValueChange = {
            validarpassword = it
        }, placeholder = {
            Text("Confirma tu contraseña")
        }, visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
        )

        Button(onClick = {

            viewModel.addUser(telefono.trim().toInt(),password)


        }) {
            Text(text = "Registrar Usuario")
        }


        when (val result = registrationResult) {
            is UserViewModel.ApiResult.Success -> {
                showToast("${result.message}")
                Log.d("REGISTER",result.message)
            }
            is UserViewModel.ApiResult.Error -> {
                showToast("${result.errorMessage}")
                Log.d("REGISTER",result.errorMessage)
            }
            else -> {
                Log.d("REGISTER",result.toString())
            }
        }

    }
}


@Composable
fun showToast(message: String) {
    val context = LocalContext.current
    val duration = Toast.LENGTH_SHORT
    val toast = Toast.makeText(context, message, duration)
    toast.show()
}