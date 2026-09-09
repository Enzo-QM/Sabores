package mx.tec.sabores.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
                    restaurants = viewModel.restaurants,
                    summaryOf = { id -> viewModel.summaryOf(id) },
                    onRestaurantClick = { id -> nav.navigate(Route.detail(id)) }
                )
            }

            composable(Route.MY_REVIEWS) {
                MyReviewsScreen(items = viewModel.myReviews)
            }

            composable(
                route = Route.DETAIL,
                arguments = listOf(navArgument(Route.ARG_RESTAURANT_ID) { type = NavType.IntType })
            ) { entry ->
                val id = entry.arguments?.getInt(Route.ARG_RESTAURANT_ID) ?: return@composable
                 val restaurant = viewModel.restaurantById(id) ?: return@composable

                RestaurantDetailScreen(
                    restaurant = restaurant,
                    summary = viewModel.summaryOf(id),
                    reviews = viewModel.reviewsOf(id),
                    onWriteReviewClick = { nav.navigate(Route.newReview(id)) },
                    onBack = { nav.popBackStack() }
                )
            }

            composable(
                route = Route.NEW_REVIEW,
                arguments = listOf(navArgument(Route.ARG_RESTAURANT_ID) { type = NavType.IntType })
            ) { entry ->
                val id = entry.arguments?.getInt(Route.ARG_RESTAURANT_ID) ?: return@composable
                val restaurant = viewModel.restaurantById(id) ?: return@composable

                val formViewModel: NewReviewViewModel = viewModel()

                NewReviewScreen(
                    restaurant = restaurant,
                    uiState = formViewModel.uiState,
                    onStarsChange = formViewModel::onStarsChange,
                    onCommentChange = formViewModel::onCommentChange,
                    onSave = {
                        viewModel.addReview(
                            restaurantId = id,
                            stars = formViewModel.uiState.stars,
                            comment = formViewModel.uiState.comment
                        )
                        nav.popBackStack()
                    },
                    onCancel = { nav.popBackStack() }
                )
            }
        }
    }
}
