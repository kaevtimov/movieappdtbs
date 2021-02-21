package source.kevtimov.movieappdtbs

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import java.lang.StringBuilder

private const val BASE_URL = "https://api.themoviedb.org/4/"
private const val API_KEY = "6b2a833015e45c535a0abaae9ffb48cb"
const val REQUEST_TOKEN = "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2YjJhODMzMDE1ZTQ1YzUzNWEwYWJhYWU5ZmZiNDhjYiIsInN1YiI6IjYwMzIwZWM4MGE1MTdjMDAzZTk0MGViZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.D5uc9vhqSk1cb2pwkXG37w9mCc8jXKTVhE2d-fMqnns"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val okhttp = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi)/*CoroutineCallAdapterFactory()*/)
    .baseUrl(BASE_URL)
    .client(okhttp)
    .build()

interface ApiService {

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("auth/request_token")
    fun getRequestToken(@Header("Authorization") auth : String)
    :Call<RequestTokenResponse>

    @GET("")
    fun getMovies(): Call<List<Movie>>
}

object MovieApi {
    val retrofitService : ApiService by lazy { retrofit.create(ApiService::class.java) }
}

data class Movie(private val o : String)
data class RequestTokenResponse(
    @Json(name = "request_token") private val token : String?,
    @Json(name = "status_code") private val statusCode: Int?,
    @Json(name = "status_message") private val message: String?,
    @Json(name = "status") private val status: Boolean?

)