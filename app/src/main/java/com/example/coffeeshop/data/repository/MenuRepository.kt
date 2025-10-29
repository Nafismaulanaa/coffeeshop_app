package com.example.coffeeshop.data.repository

import com.example.coffeeshop.R
import com.example.coffeeshop.data.model.MenuItem

object MenuRepository {

    fun getMenuList(): List<MenuItem> {
        return listOf(
            MenuItem(
                id = 1,
                name = "Espresso",
                description = "Kopi hitam pekat tanpa gula, pilihan tepat untuk pecinta kopi sejati.",
                price = 18000.0,
                imageResId = R.drawable.espresso
            ),
            MenuItem(
                id = 2,
                name = "Cappuccino",
                description = "Kombinasi espresso, susu panas, dan buih susu yang lembut.",
                price = 25000.0,
                imageResId = R.drawable.cappuccino
            ),
            MenuItem(
                id = 3,
                name = "Latte",
                description = "Perpaduan espresso dan susu dengan cita rasa lembut dan creamy.",
                price = 27000.0,
                imageResId = R.drawable.latte
            ),
            MenuItem(
                id = 4,
                name = "Americano",
                description = "Espresso yang dicampur air panas, rasa ringan tapi tetap beraroma kuat.",
                price = 20000.0,
                imageResId = R.drawable.americano
            ),
            MenuItem(
                id = 5,
                name = "Mocha",
                description = "Kopi dengan campuran cokelat dan susu, manis dan nikmat.",
                price = 29000.0,
                imageResId = R.drawable.mocha
            )
        )
    }
}
