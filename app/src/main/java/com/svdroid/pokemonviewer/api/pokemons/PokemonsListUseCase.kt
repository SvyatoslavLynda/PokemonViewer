package com.svdroid.pokemonviewer.api.pokemons

import com.svdroid.pokemonviewer.api.PokemonApiService
import com.svdroid.pokemonviewer.utils.BaseUseCase

/**
 * Created by SVDroid on 6/30/21.
 */
class PokemonsListUseCase : BaseUseCase<PokemonsListUseCase.Params, PokemonsListUseCase.Result>() {

    override suspend fun doWork(params: Params): Result {
        return Result(
                PokemonsListRepository()
                        .doWork(PokemonsListRepository.Params())
                        .response
        )
    }

    class Params
    class Result(val response: PokemonApiService.PokemonListResponse?)
}