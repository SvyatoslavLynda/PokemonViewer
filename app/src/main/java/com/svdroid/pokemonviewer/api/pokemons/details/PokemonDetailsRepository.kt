package com.svdroid.pokemonviewer.api.pokemons.details

import com.svdroid.pokemonviewer.api.PokemonApiService
import com.svdroid.pokemonviewer.utils.BaseRepository

/**
 * Created by SVDroid on 6/30/21.
 */
class PokemonDetailsRepository :
        BaseRepository<PokemonDetailsRepository.Params, PokemonDetailsRepository.Result>() {

    override suspend fun doWork(params: Params): Result {
        val service = PokemonApiService.create()
        val result = service
                .getPokemonDetailsAsync(params.name)
                .await()

        val response = result.body()
        return Result(response)
    }

    class Params(val name: String?)
    data class Result(val response: PokemonApiService.PokemonDetailsResponse?)
}