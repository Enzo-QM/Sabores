package mx.tec.sabores.domain

sealed interface ReviewError {
    data object NoStars : ReviewError
    data object CommentTooShort : ReviewError
    data object CommentTooLong : ReviewError
}

object ReviewValidator {
    const val COMMENT_MIN = 15
    const val COMMENT_MAX = 240

    fun validateStars(stars: Int): ReviewError? =
        if (stars !in 1..5) ReviewError.NoStars else null

    fun validateComment(comment: String): ReviewError? {
        val text = comment.trim()
        return when {
            text.length < COMMENT_MIN -> ReviewError.CommentTooShort
            text.length > COMMENT_MAX -> ReviewError.CommentTooLong
            else -> null
        }
    }

    fun isValid(stars: Int, comment: String): Boolean =
        validateStars(stars) == null && validateComment(comment) == null
}
