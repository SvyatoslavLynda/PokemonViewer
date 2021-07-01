package com.svdroid.pokemonviewer.api

import android.util.Log
import com.google.gson.annotations.SerializedName
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by SVDroid on 6/30/21.
 */
interface PokemonApiService {
    @GET("/api/v2/pokemon?limit=${Int.MAX_VALUE}")
    fun getPokemonsAsync(): Deferred<Response<PokemonListResponse>>

    @GET("/api/v2/pokemon/{name}")
    fun getPokemonDetailsAsync(@Path("name") name: String?): Deferred<Response<PokemonDetailsResponse>>

    data class PokemonListResponse(
            @SerializedName("count") val count: Int,
            @SerializedName("next") val next: String?,
            @SerializedName("previous") val previous: String?,
            @SerializedName("results") val results: List<PokemonListItemResponse>
    )

    data class PokemonListItemResponse(
            @SerializedName("name") val name: String?,
            @SerializedName("url") val url: String?
    )

    data class PokemonDetailsResponse(
            @SerializedName("id") val id: Long,
            @SerializedName("base_experience") val baseExperience: Int,
            @SerializedName("weight") val weight: Int,
            @SerializedName("height") val height: Int,
            @SerializedName("name") val name: String?,
            @SerializedName("abilities") val abilities: List<PokemonAbilitiesResponse?>
    )

    data class PokemonAbilitiesResponse(
            @SerializedName("ability") val ability: PokemonAbilityResponse,
            @SerializedName("is_hidden") val isHidden: Boolean,
    ) {
        override fun toString(): String {
            return ability.name.toString()
        }
    }

    data class PokemonAbilityResponse(
            @SerializedName("name") val name: String?,
    )

    companion object {
        private const val BASE_URL = "https://pokeapi.co/"

        fun create(): PokemonApiService {
            val logger = HttpLoggingInterceptor { Log.d("POKE-API", it) }
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                    .addInterceptor(logger)
                    .build()
            return Retrofit.Builder()
                    .baseUrl(HttpUrl.parse(BASE_URL)!!)
                    .client(client)
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(PokemonApiService::class.java)
        }

        fun getPokemonPNGImageURL(pokemonId: Long): String =
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemonId}.png"
    }
}