package com.example.navdrawer.screens.register

import android.app.Application
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
import androidx.compose.runtime.DisposableEffect
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
import androidx.navigation.NavHostController
import com.example.navdrawer.model.UserLoginResponse
import com.example.navdrawer.model.UserRegistrationResponse
import com.example.navdrawer.screens.organizations.showToast
import com.example.navdrawer.service.UserService
import com.example.navdrawer.viewModel.AppViewModel
import com.example.navdrawer.viewModel.UserViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun RegisterPage(
    appViewModel: AppViewModel = AppViewModel(Application()),
    navController: NavHostController,
) {

    val viewModel = UserViewModel(UserService.instance)
    val showDelayedText = remember { mutableStateOf(false) }

    val telefono = remember {
        mutableStateOf("")
    }

    val password = remember {
        mutableStateOf("")
    }

    val validarpassword = remember {
        mutableStateOf("")
    }

    val registrationResult = remember { mutableStateOf(UserRegistrationResponse()) }



    LaunchedEffect(key1 = viewModel) {
        viewModel.registrationResult.collect { result ->
            if (result != null) {
                registrationResult.value = result

                showDelayedText.value = true



            }
        }
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text("Registrar Usuario", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)

        TextField(value = telefono.value, onValueChange = {
            telefono.value = it
        }, placeholder = {
            Text("Teléfono de contacto")
        })

        TextField(
            value = password.value,
            onValueChange = {
                password.value = it
            },
            placeholder = {
                Text("Contraseña")
            },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
        )

        TextField(value = validarpassword.value, onValueChange = {
            validarpassword.value = it
        }, placeholder = {
            Text("Confirma tu contraseña")
        }, visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
        )

        Button(onClick = {

            viewModel.addUser(telefono.value.trim().toInt(), password.value)


        }) {
            Text(text = "Registrar Usuario")
        }

        LaunchedEffect(showDelayedText) {
            if (showDelayedText.value) {
                launch {

                    delay(5000) // Delay for 2 seconds (adjust as needed)
                    showDelayedText.value = false
                    navController.navigate("LoginPage")
                }
            }
        }


        if (showDelayedText.value) {

            Text(text = "Registro Exitoso")
            Text(text = "En 5 segundos serás redirigido a la pantalla para iniciar sesión.")
        }


    }
}
