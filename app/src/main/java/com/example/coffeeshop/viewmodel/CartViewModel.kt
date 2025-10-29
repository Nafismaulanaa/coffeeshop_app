package com.example.coffeeshop.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.coffeeshop.data.model.CartItem
import com.example.coffeeshop.data.model.MenuItem

class CartViewModel : ViewModel() {
    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: SnapshotStateList<CartItem> get() = _cartItems

    fun addItem(menuItem: MenuItem, quantity: Int = 1) {
        val existingItem = _cartItems.find { it.menuItem.id == menuItem.id }
        if (existingItem != null) {
            existingItem.quantity += quantity
            _cartItems[_cartItems.indexOf(existingItem)] = existingItem
        } else {
            _cartItems.add(CartItem(menuItem, quantity))
        }
    }

    fun removeItem(cartItem: CartItem) {
        _cartItems.remove(cartItem)
    }

    fun updateQuantity(cartItem: CartItem, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeItem(cartItem)
        } else {
            val index = _cartItems.indexOf(cartItem)
            if (index != -1) {
                _cartItems[index] = cartItem.copy(quantity = newQuantity)
            }
        }
    }

    fun clearCart() {
        _cartItems.clear()
    }

    fun getTotalPrice(): Double = _cartItems.sumOf { it.totalPrice }

    fun getTotalItems(): Int = _cartItems.sumOf { it.quantity }
}