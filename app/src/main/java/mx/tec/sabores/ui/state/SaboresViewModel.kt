package mx.tec.sabores.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.tec.sabores.data.RestaurantRepository
import mx.tec.sabores.domain.RatingSummary
import mx.tec.sabores.domain.Restaurant
import mx.tec.sabores.domain.RestaurantEnLista
import mx.tec.sabores.domain.Review

data class MyReviewItem(val restaurantName: String, val review: Review)

/** El restaurante y sus reseñas, que la pantalla de detalle necesita juntos. */
data class Detalle(
    val restaurant: Restaurant,
    val reviews: List<Review>
) {
    val summary: RatingSummary = RatingSummary.from(reviews)
}

class SaboresViewModel(
    private val repository: RestaurantRepository = RestaurantRepository()
) : ViewModel() {

    // Ya no se lee una vez al construir: ahora llega de la red, y tarda.
    var restaurantes by mutableStateOf<List<RestaurantEnLista>>(emptyList())
        private set

    var detalle by mutableStateOf<Detalle?>(null)
        private set

    var mias by mutableStateOf<List<MyReviewItem>>(emptyList())
        private set

    init { cargarRestaurantes() }

    fun cargarRestaurantes() {
        viewModelScope.launch {
            restaurantes = repository.getAllForList()
        }
    }

    fun cargarDetalle(id: Int) {
        viewModelScope.launch {
            detalle = Detalle(repository.getById(id), repository.getReviews(id))
        }
    }
}