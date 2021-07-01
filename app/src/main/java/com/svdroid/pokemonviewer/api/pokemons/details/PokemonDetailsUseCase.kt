package com.svdroid.pokemonviewer.api.pokemons.details

import com.svdroid.pokemonviewer.api.PokemonApiService
import com.svdroid.pokemonviewer.utils.BaseUseCase

/**
 * Created by SVDroid on 6/30/21.
 */
class PokemonDetailsUseCase :
        BaseUseCase<PokemonDetailsUseCase.Params, PokemonDetailsUseCase.Result>() {

    override suspend fun doWork(params: Params): Result {
        return Result(
                PokemonDetailsRepository()
                        .doWork(PokemonDetailsRepository.Params(params.name))
                        .response
        )
    }

    class Params(val name: String?)
    class Result(val response: PokemonApiService.PokemonDetailsResponse?)
}