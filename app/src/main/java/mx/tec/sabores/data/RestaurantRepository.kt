package mx.tec.sabores.data

import mx.tec.sabores.data.remote.EditReviewBody
import mx.tec.sabores.data.remote.Network
import mx.tec.sabores.data.remote.NewReviewBody
import mx.tec.sabores.data.remote.SaboresApi
import mx.tec.sabores.data.remote.toDomain
import mx.tec.sabores.data.remote.toSummary
import mx.tec.sabores.domain.Restaurant
import mx.tec.sabores.domain.RestaurantEnLista
import mx.tec.sabores.domain.Review
import retrofit2.HttpException

class RestaurantRepository(private val api: SaboresApi = Network.api) {

    suspend fun getAll(): List<Restaurant> =
        api.getRestaurants().map { it.toDomain() }

    suspend fun getById(id: Int): Restaurant =
        api.getRestaurant(id).toDomain()

    suspend fun getReviews(restaurantId: Int): List<Review> =
        api.getReviews(restaurantId).map { it.toDomain() }

    suspend fun getMyReviews(): List<Review> =
        api.getMyReviews().map { it.toDomain() }

    suspend fun addReview(restaurantId: Int, stars: Int, comment: String): Review =
        api.createReview(NewReviewBody(restaurantId, stars, comment)).toDomain()

    suspend fun editReview(id: Int, stars: Int? = null, comment: String? = null): Review =
        api.editReview(id, EditReviewBody(stars, comment)).toDomain()

    suspend fun deleteReview(id: Int): Boolean {
        val response = api.deleteReview(id)
        if (!response.isSuccessful) {
            throw HttpException(response)
        }
        return response.isSuccessful
    }

    suspend fun getAllForList(): List<RestaurantEnLista> =
        api.getRestaurants().map { RestaurantEnLista(it.toDomain(), it.toSummary()) }
}
