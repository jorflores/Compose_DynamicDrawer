package com.example.navdrawer.screens.organizations

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

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navdrawer.model.OrgRegister
import com.example.navdrawer.model.OrgRegisterResponse
import com.example.navdrawer.service.OrgService
import com.example.navdrawer.util.constants.Constants
import com.example.navdrawer.viewModel.AppViewModel
import com.example.navdrawer.viewModel.OrgViewModel

@Preview(showBackground = true)
@Composable
fun RegisterOrgPage(appViewModel: AppViewModel = AppViewModel(Application())) {

    val orgViewModel = OrgViewModel(OrgService.instance)

    val name = remember {
        mutableStateOf("")
    }

    val email = remember {
        mutableStateOf("")
    }

    val description = remember {
        mutableStateOf("")
    }

    val orgRegisterResult = remember {
        mutableStateOf(OrgRegisterResponse())
    }


    LaunchedEffect(orgViewModel) {
        orgViewModel.orgRegisterResult.collect { result ->
            if (result != null) {
                orgRegisterResult.value = result
            }
        }
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text("Agregar Nueva Organizacion", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)

        TextField(value = name.value, onValueChange = {
            name.value = it
        }, placeholder = {
            Text("Nombre")
        })

        TextField(
            value = email.value,
            onValueChange = {
                email.value = it
            },
            placeholder = {
                Text("Email")
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
        )

        TextField(value = description.value, onValueChange = {
            description.value = it
        }, placeholder = {
            Text("Descripcion")
        },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
        )

        Button(onClick = {

            val organization = OrgRegister(description.value,email.value,name.value)

            orgViewModel.addOrganization(appViewModel.getToken(),organization)


        }) {
            Text(text = "Registrar Nueva Organización")
        }

        if (orgRegisterResult.value.message != null ){
            showToast(message = "Organizacion registrada exitosamente")
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