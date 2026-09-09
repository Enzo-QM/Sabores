package mx.tec.sabores.domain

/**
 * Un restaurante con su calificación, tal como se muestra en la lista.
 * Vive en el dominio, no en ui/: si viviera en ui/, la capa de datos tendría
 * que importar de la capa de arriba para poder devolverlo.
 */
data class RestaurantEnLista(
    val restaurant: Restaurant,
    val summary: RatingSummary
)