package com.example.coffeeshop.tampilan.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.coffeeshop.data.model.MenuItem
import com.example.coffeeshop.data.repository.MenuRepository
import com.example.coffeeshop.tampilan.components.MenuItemCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    onMenuClick: (MenuItem) -> Unit
) {
    val menuList = MenuRepository.getMenuList()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Menu Kopi Senja") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(bottom = 70.dp)
        ) {
            items(menuList) { menuItem ->
                MenuItemCard(menuItem = menuItem, onClick = onMenuClick)
            }
        }
    }
}
