package mx.tec.sabores.domain

data class Review(
    val id: Int,
    val restaurantId: Int,
    val author: String,
    val stars: Int,
    val comment: String
)