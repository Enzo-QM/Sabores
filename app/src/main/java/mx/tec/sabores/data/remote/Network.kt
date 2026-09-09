package mx.tec.sabores.data.remote

import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object Network {

    private const val BASE_URL = "https://startdroid.com/api/"

    /** ⚠️ Cambia esto por TU matrícula antes de correr la app. */
    var alumno: String = "a01276322"

    private val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    /** Firma cada petición. Así es como viajan las credenciales de verdad. */
    private val identidad = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("X-Alumno", alumno)
            .build()
        chain.proceed(request)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(identidad)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        })
        .build()

    val api: SaboresApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(SaboresApi::class.java)
}