package com.example.coffeeshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.example.coffeeshop.ui.theme.CoffeeShopTheme
import com.example.coffeeshop.CoffeeShopApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoffeeShopTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    CoffeeShopApp()
                }
            }
        }
    }
}