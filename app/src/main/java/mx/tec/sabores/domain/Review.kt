package mx.tec.sabores.domain

data class Review(
    val restaurantId: Int,
    val stars: Int,
    val comment: String
)
