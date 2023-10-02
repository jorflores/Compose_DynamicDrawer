package com.example.navdrawer.screens.privacy

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.navdrawer.util.constants.Constants
import com.example.navdrawer.viewModel.AppViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPage(
    appViewModel: AppViewModel,
    onAgreeClicked: () -> Unit,
    onDisagreeClicked: () -> Unit
) {
    val isAgreed = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Aviso de Privacidad")
                }
            )
        },
        content = {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(16.dp)
                ) {
                    item {
                        Text(
                            text = Constants.avisoDePrivacidad,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    item {
                        Checkbox(
                            checked = isAgreed.value,
                            onCheckedChange = { isChecked ->
                                isAgreed.value = isChecked
                            }
                        )
                    }

                    item {
                        Text(
                            text = "I agree to the privacy agreement",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.clickable {
                                isAgreed.value = !isAgreed.value
                            }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    item {
                        Button(
                            onClick = {
                                if (isAgreed.value) {
                                    // User has accepted the privacy agreement
                                    onAgreeClicked()
                                } else {
                                    // Show a message or take appropriate action for non-acceptance
                                    onDisagreeClicked()
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Continue")
                        }
                    }
                }
            }
        }
    )
}