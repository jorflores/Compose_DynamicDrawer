package com.example.navdrawer.screens.organizations

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navdrawer.R
import com.example.navdrawer.viewModel.AppViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task

@Composable
fun LoginOrg(appViewModel: AppViewModel) {
    val context = LocalContext.current
    val isLoading = remember { mutableStateOf(false) }
    val googleSignInClient = getGoogleLoginAuth(context)
    val userInfo = remember { mutableStateOf<GoogleSignInAccount?>(null) } // Store user info


    val startForResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if (result.data != null) {
                    val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(intent)

                    val accountDetails = task.result
                    //Logging
                    Log.d("PRUEBA", "Email: ${accountDetails?.email}")


                    accountDetails.idToken?.let { Log.d("PRUEBA",  accountDetails.idToken) }
                    accountDetails.id?.let { Log.d("PRUEBA",  accountDetails.id) }

                    userInfo.value = accountDetails
                }
            }

        }


    Column {
        if (userInfo.value == null) {

            SignInButton(
                text = "Sign in with Google",
                loadingText = "Signing in...",
                isLoading = isLoading.value,
                icon = painterResource(id = R.drawable.ic_google_logo),
                onClick = {
                    isLoading.value = true
                    startForResult.launch(googleSignInClient?.signInIntent)
                }

            )

        } else {
            // Show user info when available
            UserInfo(userInfo.value!!,googleSignInClient)
        }
    }
}



private fun getGoogleLoginAuth(context: Context): GoogleSignInClient {


    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestIdToken("784626069081-r392adj9cdk77eevjg6otvqlb7omgvih.apps.googleusercontent.com")
        .requestId()
        .requestProfile()
        .build()
    return GoogleSignIn.getClient(context, gso)
}


@Composable
fun UserInfo(account: GoogleSignInAccount,googleSignInClient : GoogleSignInClient?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Welcome, ${account.displayName}", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(text = "Email: ${account.email}")
        // You can add more user information here as needed

        // Add a button to log out
        Button(
            onClick = {
                signOutFromGoogle(googleSignInClient)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            shape = RoundedCornerShape(6.dp)
        ) {
            Text(text = "Sign Out", modifier = Modifier.padding(6.dp))
        }

    }
}


// Function to sign out
private fun signOutFromGoogle(googleSignInClient: GoogleSignInClient?) {
    googleSignInClient?.signOut()
        ?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign-out was successful, you can perform any additional actions here
                // For example, you can reset the user info and navigate to the login screen
            } else {
                // Handle sign-out failure
                // You may want to display an error message or take appropriate action
            }
        }
}

@Composable
fun SignInButton(
    text: String,
    loadingText: String = "Signing in...",
    icon: Painter,
    isLoading: Boolean = false,
    borderColor: Color = Color.LightGray,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.clickable(
            enabled = !isLoading,
            onClick = onClick
        ),
        border = BorderStroke(width = 1.dp, color = borderColor)
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = 12.dp,
                    end = 16.dp,
                    top = 12.dp,
                    bottom = 12.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                painter = icon,
                contentDescription = "SignInButton",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(text = if (isLoading) loadingText else text)
            if (isLoading) {
                Spacer(modifier = Modifier.width(16.dp))
                CircularProgressIndicator(
                    modifier = Modifier
                        .height(16.dp)
                        .width(16.dp),
                    strokeWidth = 2.dp
                )
            }
        }
    }
}