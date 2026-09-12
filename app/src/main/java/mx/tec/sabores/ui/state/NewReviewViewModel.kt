package mx.tec.sabores.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.tec.sabores.data.RestaurantRepository
import mx.tec.sabores.domain.ReviewError
import mx.tec.sabores.domain.ReviewValidator
import retrofit2.HttpException
import java.io.IOException

data class NewReviewUiState(
    val stars: Int = 0,
    val comment: String = "",
    val guardando: Boolean = false,
    val errorAlGuardar: String? = null
) {
    // Estado DERIVADO: se calcula, no se guarda.
    val commentError: ReviewError? =
        if (comment.isEmpty()) null else ReviewValidator.validateComment(comment)

    // Con la red de por medio, "puedo guardar" incluye "no estoy guardando ya".
    val canSave: Boolean = ReviewValidator.isValid(stars, comment) && !guardando

    val charactersLeft: Int = ReviewValidator.COMMENT_MAX - comment.trim().length
}

class NewReviewViewModel(
    private val repository: RestaurantRepository = RestaurantRepository()
) : ViewModel() {

    var uiState by mutableStateOf(NewReviewUiState())
        private set

    fun onStarsChange(stars: Int) {
        uiState = uiState.copy(stars = stars, errorAlGuardar = null)
    }

    fun onCommentChange(text: String) {
        if (text.length <= ReviewValidator.COMMENT_MAX) {
            uiState = uiState.copy(comment = text, errorAlGuardar = null)
        }
    }

    fun publicar(restaurantId: Int, alTerminar: () -> Unit) {
        if (!uiState.canSave) return
        viewModelScope.launch {
            uiState = uiState.copy(guardando = true, errorAlGuardar = null)
            try {
                repository.addReview(restaurantId, uiState.stars, uiState.comment)
                uiState = uiState.copy(guardando = false)
                alTerminar()
            } catch (e: IOException) {
                uiState = uiState.copy(
                    guardando = false,
                    errorAlGuardar = "No hay conexión. Tu reseña no se publicó."
                )
            } catch (e: HttpException) {
                uiState = uiState.copy(guardando = false, errorAlGuardar = mensajeDe(e))
            }
        }
    }
}