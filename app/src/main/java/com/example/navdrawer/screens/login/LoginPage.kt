package com.example.navdrawer.screens.login

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

import com.example.navdrawer.viewModel.AppViewModel
import com.example.navdrawer.model.UserLoginResponse
import com.example.navdrawer.service.UserService
import com.example.navdrawer.util.constants.Constants
import com.example.navdrawer.viewModel.UserViewModel


@SuppressLint("StateFlowValueCalledInComposition", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginPage(
    appviewModel: AppViewModel,
    navController: NavHostController,
    onLoggedInChanged: (Boolean) -> Unit
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val userviewModel = UserViewModel(UserService.instance)

    var telefono by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var loginResult by remember {
        mutableStateOf(UserLoginResponse())
    }


    LaunchedEffect(key1 = userviewModel) {
        userviewModel.loginResult.collect { result ->
            if (result != null) {

                loginResult = result

                if (loginResult?.message != null){
                    snackbarHostState.showSnackbar(loginResult.message.toString())
                }



                loginResult.token?.let {
                    snackbarHostState.showSnackbar("Login exitoso...")
                    appviewModel.storeValueInDataStore(it, Constants.TOKEN)
                    appviewModel.setToken(it)
                    appviewModel.setLoggedIn()
                    onLoggedInChanged(true)
                    navController.navigate("HomePage")

                    Log.d("DATASTORE", "Token saved: ${it}")
                }
                loginResult.isAdmin.let {
                    appviewModel.storeValueInDataStore(it,Constants.ISADMIN)
                    appviewModel.setIsAdmin(it)
                }


            }
        }
    }

Scaffold(

    snackbarHost = {
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.padding(16.dp)
        )
    }

) {


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

            userviewModel.loginUser(telefono.trim().toInt(), password)

        }) {
            Text(text = "Ingresar")
        }
    }
}


}



