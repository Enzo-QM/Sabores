package mx.tec.sabores.data

import mx.tec.sabores.data.remote.Network
import mx.tec.sabores.data.remote.SaboresApi
import mx.tec.sabores.data.remote.toDomain
import mx.tec.sabores.domain.Restaurant
import mx.tec.sabores.domain.Review
import mx.tec.sabores.data.remote.toSummary   // ← junto a los otros imports
import mx.tec.sabores.domain.RestaurantEnLista

class RestaurantRepository(private val api: SaboresApi = Network.api) {

    suspend fun getAll(): List<Restaurant> =
        api.getRestaurants().map { it.toDomain() }

    suspend fun getById(id: Int): Restaurant =
        api.getRestaurant(id).toDomain()

    suspend fun getReviews(restaurantId: Int): List<Review> =
        api.getReviews(restaurantId).map { it.toDomain() }

    suspend fun getMyReviews(): List<Review> =
        api.getMyReviews().map { it.toDomain() }

    suspend fun getAllForList(): List<RestaurantEnLista> =
        api.getRestaurants().map { RestaurantEnLista(it.toDomain(), it.toSummary()) }

}

