package mx.tec.sabores.data.remote

import retrofit2.Response
import retrofit2.http.*

interface SaboresApi {

    @GET("restaurants")
    suspend fun getRestaurants(): List<RestaurantDto>

    @GET("restaurants/{id}")
    suspend fun getRestaurant(@Path("id") id: Int): RestaurantDto

    @GET("reviews")
    suspend fun getReviews(@Query("restaurantId") restaurantId: Int): List<ReviewDto>

    @GET("me/reviews")
    suspend fun getMyReviews(): List<ReviewDto>

    @POST("reviews")
    suspend fun createReview(@Body body: NewReviewBody): ReviewDto

    @PATCH("reviews/{id}")
    suspend fun editReview(@Path("id") id: Int, @Body body: EditReviewBody): ReviewDto

    // Response<Unit> para poder leer el código: 204 si era tuya, 403 si no.
    @DELETE("reviews/{id}")
    suspend fun deleteReview(@Path("id") id: Int): Response<Unit>
}