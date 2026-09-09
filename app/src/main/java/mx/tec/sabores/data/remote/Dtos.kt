package mx.tec.sabores.data.remote

import kotlinx.serialization.Serializable

/** Lo que el servidor manda. No es el dominio: es su envoltura de transporte. */
@Serializable
data class RestaurantDto(
    val id: Int,
    val name: String,
    val cuisine: String,
    val address: String,
    val description: String,
    val priceLevel: Int,
    val emoji: String,
    val ratingAverage: Double = 0.0,
    val ratingCount: Int = 0
)

@Serializable
data class ReviewDto(
    val id: Int,
    val restaurantId: Int,
    val author: String,
    val stars: Int,
    val comment: String,
    val createdAt: String
)

/** Lo que se manda al crear. Sin id ni autor: esos los pone el servidor. */
@Serializable
data class NewReviewBody(
    val restaurantId: Int,
    val stars: Int,
    val comment: String
)

@Serializable
data class EditReviewBody(
    val stars: Int? = null,
    val comment: String? = null
)