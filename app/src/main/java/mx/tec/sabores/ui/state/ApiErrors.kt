package mx.tec.sabores.ui.state

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import retrofit2.HttpException

/** Traduce un 4xx de la API al mensaje que ve el usuario. */
fun mensajeDe(e: HttpException): String {
    val cuerpo = e.response()?.errorBody()?.string()
    val mensaje = cuerpo
        ?.let { runCatching { Json.parseToJsonElement(it) }.getOrNull() }
        ?.jsonObject?.get("error")?.jsonPrimitive?.contentOrNull

    return when (e.code()) {
        401 -> "Falta tu matricula en Network.alumno."
        403 -> mensaje ?: "Esa resena no es tuya."
        404 -> "Eso ya no existe. Actualiza la lista."
        422 -> mensaje ?: "Los datos no son validos."
        else -> "El servidor respondio ${e.code()}."
    }
}
