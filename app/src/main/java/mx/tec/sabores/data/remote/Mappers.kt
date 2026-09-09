package mx.tec.sabores.data.remote

import mx.tec.sabores.domain.Restaurant
import mx.tec.sabores.domain.Review
import mx.tec.sabores.domain.RatingSummary

/** El promedio que el servidor ya calculó, en la forma que entiende el dominio. */
fun RestaurantDto.toSummary() = RatingSummary(average = ratingAverage, count = ratingCount)

fun RestaurantDto.toDomain() = Restaurant(
    id = id,
    name = name,
    cuisine = cuisine,
    address = address,
    description = description,
    priceLevel = priceLevel,
    emoji = emoji
)

fun ReviewDto.toDomain() = Review(
    id = id,
    restaurantId = restaurantId,
    author = author,
    stars = stars,
    comment = comment
)
