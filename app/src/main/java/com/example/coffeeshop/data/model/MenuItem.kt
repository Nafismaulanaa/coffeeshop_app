package com.example.coffeeshop.data.model

import kotlinx.serialization.Serializable

@Serializable
data class MenuItem(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imageResId: Int
)
