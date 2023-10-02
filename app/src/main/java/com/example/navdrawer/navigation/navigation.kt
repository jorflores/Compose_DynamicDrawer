package com.example.navdrawer.navigation



import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navdrawer.viewModel.AppViewModel
import com.example.navdrawer.screens.about.AboutPage
import com.example.navdrawer.screens.home.HomePage
import com.example.navdrawer.screens.login.LoginPage
import com.example.navdrawer.screens.organizations.LoginOrg
import com.example.navdrawer.screens.organizations.RegisterOrgPage
import com.example.navdrawer.screens.privacy.PrivacyPage
import com.example.navdrawer.screens.protect.TestProtectedPage
import com.example.navdrawer.screens.register.RegisterPage
import com.example.navdrawer.screens.settings.SettingsPage
import com.example.navdrawer.util.constants.Constants

import kotlinx.coroutines.launch


data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(appViewModel: AppViewModel) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navController = rememberNavController()
    val selectedItemIndex = rememberSaveable {
        mutableStateOf(0)
    }

    val showDialog = remember { mutableStateOf(false) }

    fun toggleDialog() {
        showDialog.value = !showDialog.value
    }


    /*val loggedIn = remember {
        mutableStateOf(appViewModel.isUserLoggedIn())
    }*/


    val items = if (!appViewModel.isUserLoggedIn()) mutableListOf(
        NavigationItem(
            title = "HomePage",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = "HomePage"
        ),
        NavigationItem(
            title = "About",
            selectedIcon = Icons.Filled.Info,
            unselectedIcon = Icons.Outlined.Info,
            route = "AboutPage"
        ),
        NavigationItem(
            title = "Settings",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            route = "SettingsPage"
        ),
        NavigationItem(
            title = "Registrar Nueva Cuenta",
            selectedIcon = Icons.Filled.Create,
            unselectedIcon = Icons.Outlined.Create,
            route = "RegisterPage"
        ),
        NavigationItem(
            title = "Login",
            selectedIcon = Icons.Filled.AccountCircle,
            unselectedIcon = Icons.Outlined.AccountCircle,
            route = "LoginPage"
        ),
        NavigationItem(
            title = "Org Login",
            selectedIcon = Icons.Filled.Info,
            unselectedIcon = Icons.Outlined.Info,
            route = "OrgLogin"
        ),
        NavigationItem(
            title = "Test Protected Page",
            selectedIcon = Icons.Filled.Lock,
            unselectedIcon = Icons.Outlined.Lock,
            route = "TestProtectedPage"
        )
    ) else
        mutableListOf(
            NavigationItem(
                title = "HomePage",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home,
                route = "HomePage"
            ),
            NavigationItem(
                title = "About",
                selectedIcon = Icons.Filled.Info,
                unselectedIcon = Icons.Outlined.Info,
                route = "AboutPage"
            ),
            NavigationItem(
                title = "Settings",
                selectedIcon = Icons.Filled.Settings,
                unselectedIcon = Icons.Outlined.Settings,
                route = "SettingsPage"
            ),

            NavigationItem(
                title = "Test Protected Page",
                selectedIcon = Icons.Filled.Lock,
                unselectedIcon = Icons.Outlined.Lock,
                route = "TestProtectedPage"
            )
        )


    if (appViewModel.isAdmin()) {
        items.add(
            NavigationItem(
                title = "Add Organization",
                selectedIcon = Icons.Filled.List,
                unselectedIcon = Icons.Outlined.List,
                route = "RegisterOrg"
            )
        )
    }


    ModalNavigationDrawer(drawerContent = {

        ModalDrawerSheet {

            Spacer(modifier = Modifier.height(16.dp))

            items.forEachIndexed { index, item ->
                NavigationDrawerItem(
                    label = {
                        Text(text = item.title)
                    },
                    selected = index == selectedItemIndex.value,
                    onClick = {
                        navController.navigate(item.route)
                        selectedItemIndex.value = index
                        scope.launch {
                            drawerState.close()
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = if (index == selectedItemIndex.value) {
                                item.selectedIcon
                            } else item.unselectedIcon,
                            contentDescription = item.title
                        )
                    },
                    modifier = Modifier
                        .padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }

            if (appViewModel.isUserLoggedIn()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Column {
                        Text(
                        text = "Bienvenido! Sesión activa",
                        style = TextStyle(
                            fontWeight = FontWeight.Light,
                            fontSize = 18.sp
                        )
                    )
                        Button(onClick = {



                            toggleDialog()

                        }) {
                            Text(text = "Finalizar Sesión")
                        }
                    }

                }

            }
            else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Column {
                        Text(
                        text = "Usuario invitado",
                        style = TextStyle(
                            fontWeight = FontWeight.Light,
                            fontSize = 18.sp
                        )
                    )
                        Text(
                            text = "Registra una cuenta o inicia sesión para aprovechar al máximo la aplicación. ",
                            style = TextStyle(
                                fontWeight = FontWeight.Light,
                                fontSize = 14.sp
                            )
                        )

                    }

                }
            }


        }
    }, drawerState = drawerState) {


        Scaffold(topBar = {
            TopAppBar(title = { Text(text = "Navigation Drawer") }, navigationIcon = {

                IconButton(onClick = {

                    if (drawerState.isClosed) {
                        scope.launch {
                            drawerState.open()
                        }
                    } else {
                        scope.launch {
                            drawerState.close()
                        }
                    }


                }) {
                    Icon(Icons.Filled.Menu, contentDescription = "Drawer Menu.")
                }

            })
        }) {

            Box(modifier = Modifier.padding(it)) {

                NavHost(navController = navController, startDestination = "HomePage") {

                    composable("HomePage") {


                        HomePage(appViewModel,navController)
                    }

                    composable("AboutPage") {
                        AboutPage(appViewModel,navController)
                    }

                    composable("RegisterPage") {
                        RegisterPage(appViewModel,navController)
                    }

                    composable("RegisterOrg") {
                        RegisterOrgPage(appViewModel)
                    }

                    composable("OrgLogin") {
                        LoginOrg(appViewModel)
                    }

                    composable("Privacy"){
                        PrivacyPage(appViewModel,onAgreeClicked = {

                            appViewModel.storeValueInDataStore(true,Constants.SIGNED_PRIVACY)
                            appViewModel.setSignedPrivacy()
                            // Handle what should happen when the user agrees
                            // For example, navigate to the next screen
                            navController.navigate("HomePage")
                        }
                        ) {
                            // Handle what should happen when the user disagrees
                            // For example, show a message or take appropriate action
                            // You can also navigate to a different screen if needed
                            //navController.navigate("DisagreeScreen")
                        }
                    }


                    composable("LoginPage") {
                        LoginPage(appViewModel,navController) { value ->
                            //loggedIn.value = value
                            appViewModel.setLoggedIn(value)
                        }
                    }


                    composable("TestProtectedPage") {
                        TestProtectedPage(appViewModel)
                    }


                    composable("SettingsPage") {
                        SettingsPage(appViewModel)
                    }
                }
            }
        }
    }


    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { toggleDialog() },
            title = { Text(text = "Confirm Logout") },
            text = { Text(text = "Are you sure you want to log out?") },
            confirmButton = {
                Button(
                    onClick = {
                        // Perform logout action here
                        // You can use navController to navigate to the login page
                        navController.navigate("LoginPage")
                        //loggedIn.value = false // Update your loggedIn state
                        appViewModel.setLoggedIn(false)
                        appViewModel.deleteToken()
                        appViewModel.setLoggedOut()
                        toggleDialog() // Close the dialog
                        scope.launch {
                            drawerState.close()
                        }
                    }
                ) {
                    Text(text = "Logout")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        toggleDialog() // Close the dialog
                    }
                ) {
                    Text(text = "Cancel")
                }
            }
        )
    }



}




