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
import java.io.IOException
import retrofit2.HttpException

data class MyReviewItem(val restaurantName: String, val review: Review)

/** El restaurante y sus reseñas, que la pantalla de detalle necesita juntos. */
data class Detalle(
    val restaurant: Restaurant,
    val reviews: List<Review>
) {
    // La regla del dominio sigue viva: el promedio se calcula aquí, no se hereda
    // del servidor, para que cambie al instante al publicar tu reseña.
    val summary: RatingSummary = RatingSummary.from(reviews)
}

class SaboresViewModel(
    private val repository: RestaurantRepository = RestaurantRepository()
) : ViewModel() {

    var restaurantes by mutableStateOf<UiState<List<RestaurantEnLista>>>(UiState.Cargando)
        private set

    var detalle by mutableStateOf<Detalle?>(null)
        private set

    var mias by mutableStateOf<List<MyReviewItem>>(emptyList())
        private set

    var mensajeError by mutableStateOf<String?>(null)
        private set

    init {
        cargarRestaurantes()
        cargarMisResenas()
    }

    fun limpiarError() {
        mensajeError = null
    }

    fun cargarRestaurantes() {
        viewModelScope.launch {
            restaurantes = UiState.Cargando
            restaurantes = pedir { repository.getAllForList() }
        }
    }

    fun cargarDetalle(id: Int) {
        viewModelScope.launch {
            val resultado = pedir { Detalle(repository.getById(id), repository.getReviews(id)) }
            if (resultado is UiState.Exito) {
                detalle = resultado.datos
            }
        }
    }

    fun cargarMisResenas() {
        viewModelScope.launch {
            try {
                val myReviews = repository.getMyReviews()
                val restaurantsMap = repository.getAll().associateBy { it.id }
                mias = myReviews.map { review ->
                    val name = restaurantsMap[review.restaurantId]?.name ?: "Restaurante"
                    MyReviewItem(restaurantName = name, review = review)
                }
            } catch (e: Exception) {
                // Manejar error de forma segura
            }
        }
    }

    fun borrarResena(reviewId: Int, restaurantId: Int? = null) {
        viewModelScope.launch {
            try {
                repository.deleteReview(reviewId)
                cargarMisResenas()
                cargarRestaurantes()
                if (restaurantId != null) {
                    cargarDetalle(restaurantId)
                }
            } catch (e: HttpException) {
                mensajeError = mensajeDe(e)
            } catch (e: Exception) {
                mensajeError = "No hay conexión. No se pudo borrar la reseña."
            }
        }
    }

    fun editarResena(reviewId: Int, stars: Int? = null, comment: String? = null, restaurantId: Int? = null) {
        viewModelScope.launch {
            try {
                repository.editReview(reviewId, stars, comment)
                cargarMisResenas()
                cargarRestaurantes()
                if (restaurantId != null) {
                    cargarDetalle(restaurantId)
                }
            } catch (e: HttpException) {
                mensajeError = mensajeDe(e)
            } catch (e: Exception) {
                mensajeError = "No hay conexión. No se pudo editar la reseña."
            }
        }
    }

    private suspend fun <T> pedir(block: suspend () -> T): UiState<T> = try {
        UiState.Exito(block())
    } catch (e: IOException) {
        UiState.Error("No hay conexión. Revisa tu internet.")
    } catch (e: HttpException) {
        UiState.Error(mensajeDe(e))
    }
}
