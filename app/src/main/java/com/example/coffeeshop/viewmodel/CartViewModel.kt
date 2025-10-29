package com.example.coffeeshop.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.coffeeshop.data.model.MenuItem

class CartViewModel : ViewModel() {
    private val _cartItems = mutableStateListOf<MenuItem>()
    val cartItems: SnapshotStateList<MenuItem> get() = _cartItems

    fun addItem(item: MenuItem) {
        _cartItems.add(item)
    }

    fun clearCart() {
        _cartItems.clear()
    }

    fun getTotalPrice(): Double = _cartItems.sumOf { it.price }
}
