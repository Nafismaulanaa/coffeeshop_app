package com.example.coffeeshop.tampilan.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.coffeeshop.data.model.MenuItem
import com.example.coffeeshop.tampilan.screen.CartScreen
import com.example.coffeeshop.tampilan.screen.DetailMenuScreen
import com.example.coffeeshop.tampilan.screen.MenuScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import com.example.coffeeshop.viewmodel.CartViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

sealed class Screen(val route: String, val title: String) {
    object Menu : Screen("menu", "Menu")
    object Cart : Screen("cart", "Keranjang")
    object Detail : Screen("detail/{menuItem}", "Detail") {
        fun createRoute(menuItem: MenuItem): String {
            val itemJson = Json.encodeToString(menuItem)
            return "detail/$itemJson"
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val cartViewModel: CartViewModel = viewModel()

    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Menu.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Menu.route) {
                MenuScreen(
                    onMenuClick = { menuItem ->
                        navController.navigate(Screen.Detail.createRoute(menuItem))
                    }
                )
            }

            composable(Screen.Cart.route) {
                CartScreen(cartViewModel = cartViewModel)
            }

            composable(Screen.Detail.route) { backStackEntry ->
                val json = backStackEntry.arguments?.getString("menuItem")
                val menuItem = json?.let { Json.decodeFromString<MenuItem>(it) }
                menuItem?.let { item ->
                    DetailMenuScreen(
                        menuItem = item,
                        cartViewModel = cartViewModel,
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(Screen.Menu, Screen.Cart)
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                label = { Text(screen.title) },
                icon = {
                    Icon(
                        imageVector = if (screen is Screen.Menu)
                            Icons.Default.Coffee
                        else
                            Icons.Default.ShoppingCart,
                        contentDescription = screen.title
                    )
                }
            )
        }
    }
}
