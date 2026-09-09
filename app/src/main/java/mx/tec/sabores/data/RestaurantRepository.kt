package mx.tec.sabores.data

import mx.tec.sabores.domain.Restaurant

class RestaurantRepository {

    private val restaurants = listOf(
        Restaurant(1, "La Chinampa", "Mexicana", "Av. Garza Sada 300",
            "Cocina de mercado: tacos de guisado, sopes y agua del día.", 1, "🌮"),
        Restaurant(2, "Nonna Rosa", "Italiana", "Río Nazas 118",
            "Pasta fresca hecha en casa y horno de leña a la vista.", 3, "🍝"),
        Restaurant(3, "Kaze", "Japonesa", "Vasconcelos 402",
            "Barra de sushi de doce lugares. Menú corto que cambia cada semana.", 3, "🍣"),
        Restaurant(4, "Verde Limón", "Vegetariana", "Hidalgo 1204",
            "Bowls de temporada y jugos prensados en frío. Todo sin carne.", 2, "🥗"),
        Restaurant(5, "Brasa 33", "Parrilla", "Gómez Morín 33",
            "Cortes al carbón y papas rústicas. Reserva el fin de semana.", 3, "🥩")
    )

    fun getAll(): List<Restaurant> = restaurants

    fun getById(id: Int): Restaurant? = restaurants.firstOrNull { it.id == id }
}
