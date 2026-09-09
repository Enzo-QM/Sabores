package mx.tec.sabores.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

enum class MenuItem(val route: String, val label: String, val icon: ImageVector) {
    HOME(Route.HOME, "Restaurantes", Icons.Default.Home),
    MY_REVIEWS(Route.MY_REVIEWS, "Mis reseñas", Icons.Default.Star)
}
