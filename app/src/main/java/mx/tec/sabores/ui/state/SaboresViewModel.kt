package mx.tec.sabores.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import mx.tec.sabores.data.RestaurantRepository
import mx.tec.sabores.domain.RatingSummary
import mx.tec.sabores.domain.Restaurant
import mx.tec.sabores.domain.Review
import mx.tec.sabores.domain.ReviewValidator

data class MyReviewItem(val restaurantName: String, val review: Review)

class SaboresViewModel : ViewModel() {

    private val repository = RestaurantRepository()

    // Estado que NO cambia: se lee una vez.
    val restaurants: List<Restaurant> = repository.getAll()

    // Estado que SI cambia: Compose se suscribe y recompone solo.
    var reviews by mutableStateOf<List<Review>>(emptyList())
        private set

    fun restaurantById(id: Int): Restaurant? = repository.getById(id)

    fun reviewsOf(restaurantId: Int): List<Review> =
        reviews.filter { it.restaurantId == restaurantId }

    fun summaryOf(restaurantId: Int): RatingSummary =
        RatingSummary.from(reviewsOf(restaurantId))

    val myReviews: List<MyReviewItem>
        get() = reviews.reversed().mapNotNull { review ->
            restaurantById(review.restaurantId)?.let { MyReviewItem(it.name, review) }
        }

    // --- eventos que llegan desde la UI ---

    fun addReview(restaurantId: Int, stars: Int, comment: String) {
        if (!ReviewValidator.isValid(stars, comment)) return
        reviews = reviews + Review(restaurantId, stars, comment.trim())
    }
}
