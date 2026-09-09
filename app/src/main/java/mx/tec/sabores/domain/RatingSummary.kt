package mx.tec.sabores.domain

data class RatingSummary(
    val average: Double,
    val count: Int
) {
    val hasReviews: Boolean get() = count > 0

    companion object {
        fun from(reviews: List<Review>): RatingSummary {
            if (reviews.isEmpty()) return RatingSummary(average = 0.0, count = 0)
            return RatingSummary(
                average = reviews.sumOf { it.stars }.toDouble() / reviews.size,
                count = reviews.size
            )
        }
    }
}
