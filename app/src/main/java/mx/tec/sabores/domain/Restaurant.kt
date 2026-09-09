package mx.tec.sabores.domain

data class Restaurant(
    val id: Int,
    val name: String,
    val cuisine: String,
    val address: String,
    val description: String,
    val priceLevel: Int,   // 1, 2 o 3
    val emoji: String
) {
    val priceLabel: String get() = "$".repeat(priceLevel)
}
