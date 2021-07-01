package com.svdroid.pokemonviewer.api.pokemons

import com.svdroid.pokemonviewer.api.PokemonApiService
import com.svdroid.pokemonviewer.utils.BaseRepository

/**
 * Created by SVDroid on 6/30/21.
 */
class PokemonsListRepository :
        BaseRepository<PokemonsListRepository.Params, PokemonsListRepository.Result>() {

    override suspend fun doWork(params: Params): Result {
        val service = PokemonApiService.create()
        val result = service
                .getPokemonsAsync()
                .await()

        val response = result.body()
        return Result(response)
    }

    class Params
    data class Result(val response: PokemonApiService.PokemonListResponse?)
}