package mx.tec.sabores.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.sabores.data.RestaurantRepository
import mx.tec.sabores.domain.RatingSummary
import mx.tec.sabores.domain.Restaurant
import mx.tec.sabores.domain.RestaurantEnLista
import mx.tec.sabores.ui.components.RestaurantCard
import mx.tec.sabores.ui.theme.SaboresTheme

@Composable
fun RestaurantListScreen(
    restaurants: List<RestaurantEnLista>,
    onRestaurantClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(restaurants, key = { it.restaurant.id }) { item ->
            RestaurantCard(
                restaurant = item.restaurant,
                summary = item.summary,
                onClick = { onRestaurantClick(item.restaurant.id) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ListPreview() {
    SaboresTheme {
        RestaurantListScreen(
            restaurants = listOf(
                RestaurantEnLista(
                    restaurant = Restaurant(
                        id = 1,
                        name = "Tacos El Güero",
                        cuisine = "Mexicana",
                        address = "Av. Tec 123",
                        description = "Los mejores tacos",
                        priceLevel = 1,
                        emoji = "🌮"
                    ),
                    summary = RatingSummary(4.2, 3)
                )
            ),
            onRestaurantClick = {}
        )
    }
}
