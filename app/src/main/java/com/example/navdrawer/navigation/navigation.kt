package com.example.navdrawer.navigation




import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
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
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navdrawer.AppViewModel
import com.example.navdrawer.screens.about.AboutPage
import com.example.navdrawer.screens.home.HomePage
import com.example.navdrawer.screens.settings.SettingsPage

import kotlinx.coroutines.launch


data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)


@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage() {






    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    val viewModel: AppViewModel = viewModel()

    var loggedIn by remember {
      mutableStateOf(viewModel.isUserLoggedIn())
    }

    val items = if(!loggedIn) listOf(
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
        )
    ) else
        listOf(
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
                title = "LogOff",
                selectedIcon = Icons.Filled.Clear,
                unselectedIcon = Icons.Outlined.Clear,
                route = "LogOff"
            )
        )



    ModalNavigationDrawer(drawerContent = {

        ModalDrawerSheet {

            Spacer(modifier = Modifier.height(16.dp))

            items.forEachIndexed { index, item ->
                NavigationDrawerItem(
                    label = {
                        Text(text = item.title)
                    },
                    selected = index == selectedItemIndex,
                    onClick = {
                        navController.navigate(item.route)
                        selectedItemIndex = index
                        scope.launch {
                            drawerState.close()
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = if (index == selectedItemIndex) {
                                item.selectedIcon
                            } else item.unselectedIcon,
                            contentDescription = item.title
                        )
                    },
                    modifier = Modifier
                        .padding(NavigationDrawerItemDefaults.ItemPadding)
                )
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
                        HomePage(viewModel)
                    }

                    composable("AboutPage") {
                        AboutPage()
                    }

                    composable("SettingsPage") {
                        SettingsPage(viewModel, navController) { value ->
                            // Update the loggedIn state in MainPage when it changes
                            loggedIn = value
                          //  selectedItemIndex = if (value) 1 else 0
                        }
                    }
                }
            }
        }
    }
}
