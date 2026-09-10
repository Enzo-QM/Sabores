package mx.tec.sabores.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import mx.tec.sabores.ui.screens.MyReviewsScreen
import mx.tec.sabores.ui.screens.NewReviewScreen
import mx.tec.sabores.ui.screens.RestaurantDetailScreen
import mx.tec.sabores.ui.screens.RestaurantListScreen
import mx.tec.sabores.ui.state.NewReviewViewModel
import mx.tec.sabores.ui.state.SaboresViewModel

@Composable
fun SaboresApp() {
    val nav = rememberNavController()
    val viewModel: SaboresViewModel = viewModel()

    val backStackEntry by nav.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = MenuItem.entries.any { it.route == currentRoute }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    MenuItem.entries.forEach { item ->
                        NavigationBarItem(
                            selected = currentRoute == item.route,
                            onClick = {
                                nav.navigate(item.route) {
                                    popUpTo(Route.HOME) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(item.icon, contentDescription = null) },
                            label = { Text(item.label) }
                        )
                    }
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = nav,
            startDestination = Route.HOME,
            modifier = Modifier.padding(padding)
        ) {

            composable(Route.HOME) {
                RestaurantListScreen(
                    restaurants = viewModel.restaurantes,
                    onRestaurantClick = { id -> nav.navigate(Route.detail(id)) }
                )
            }

            composable(Route.MY_REVIEWS) {
                MyReviewsScreen(items = viewModel.mias)
            }

            composable(
                route = Route.DETAIL,
                arguments = listOf(navArgument(Route.ARG_RESTAURANT_ID) { type = NavType.IntType })
            ) { entry ->
                val id = entry.arguments?.getInt(Route.ARG_RESTAURANT_ID) ?: return@composable

                LaunchedEffect(id) { viewModel.cargarDetalle(id) }
                val detalle = viewModel.detalle ?: return@composable

                RestaurantDetailScreen(
                    restaurant = detalle.restaurant,
                    summary = detalle.summary,
                    reviews = detalle.reviews,
                    onWriteReviewClick = { nav.navigate(Route.newReview(id)) },
                    onBack = { nav.popBackStack() }
                )
            }

            composable(
                route = Route.NEW_REVIEW,
                arguments = listOf(navArgument(Route.ARG_RESTAURANT_ID) { type = NavType.IntType })
            ) {
                val restaurant = viewModel.detalle?.restaurant ?: return@composable

                val formViewModel: NewReviewViewModel = viewModel()

                NewReviewScreen(
                    restaurant = restaurant,
                    uiState = formViewModel.uiState,
                    onStarsChange = formViewModel::onStarsChange,
                    onCommentChange = formViewModel::onCommentChange,
                    onSave = {
                        // Todavía no guarda: publicar contra el servidor es el Bloque C.
                        nav.popBackStack()
                    },
                    onCancel = { nav.popBackStack() }
                )
            }
        }
    }
}
