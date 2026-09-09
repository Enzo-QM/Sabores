package mx.tec.sabores.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import mx.tec.sabores.domain.ReviewError
import mx.tec.sabores.domain.ReviewValidator

data class NewReviewUiState(
    val stars: Int = 0,
    val comment: String = ""
) {
    // Estado DERIVADO: se calcula, no se guarda.
    val commentError: ReviewError? =
        if (comment.isEmpty()) null else ReviewValidator.validateComment(comment)

    val canSave: Boolean = ReviewValidator.isValid(stars, comment)

    val charactersLeft: Int = ReviewValidator.COMMENT_MAX - comment.trim().length
}

class NewReviewViewModel : ViewModel() {

    var uiState by mutableStateOf(NewReviewUiState())
        private set

    fun onStarsChange(stars: Int) {
        uiState = uiState.copy(stars = stars)
    }

    fun onCommentChange(text: String) {
        if (text.length <= ReviewValidator.COMMENT_MAX) {
            uiState = uiState.copy(comment = text)
        }
    }
}
