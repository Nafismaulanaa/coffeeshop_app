- Nama Anggota
1. Nafis Ilyas Maulana (23523014)
2. Kelvin Oktabrian Ramadhan (23523089)
3. Arzal Nabil Qitfirul (23523244)

- Pola navigasi yg digunakan
Aplikasi ini menggunakan pola navigasi dengan Bottom Navigation dari Jetpack Compose. Pola ini memungkinkan untuk berpindah dari halaman utama dengan mudah melalui navigasi di bagian bawah aplikasi.

- Struktur navigasi
1. MenuScreen (Menampilkan daftar menu kopi.)
2. CartScreen (Menampilkan daftar pesanan yang telah dimasukkan ke keranjang.)
3. DetailMenuScreen (Menampilkan detail menu yang dipilih dari MenuScreen.)

- Navigasi dikelola menggunakan NavHostController dan NavHost, dengan route yang ditentukan dalam kelas Screen sebagai berikut:
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

- Navigasi antar screen diatur di file AppNavigation.kt salah menggunakan komponen:
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

- Mekanisme pengiriman data antar screen
Pengiriman data antar layar dilakukan menggunakan objek MenuItem yang ditandai dengan @Serializable.
@Serializable
data class MenuItem(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imageResId: Int
)

- Mekanisme pengiriman data dari MenuScreen ke DetailMenuScreen
Saat pengguna menekan salah satu menu, objek MenuItem dikonversi ke format JSON, lalu dikirim melalui route:
val itemJson = Json.encodeToString(menuItem)
navController.navigate("detail/$itemJson")

- Di DetailMenuScreen, data dikembalikan ke objek Kotlin dengan:
val json = backStackEntry.arguments?.getString("menuItem")
val menuItem = json?.let { Json.decodeFromString<MenuItem>(it) }

- Dari DetailMenuScreen ke CartScreen, proses pengiriman dilakukan lewat ViewModel (CartViewModel) agar data keranjang bersifat global dan tetap tersimpan saat navigasi berganti sebagai berikut:
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
