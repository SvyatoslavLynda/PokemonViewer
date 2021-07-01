package com.svdroid.pokemonviewer.db

import com.svdroid.pokemonviewer.api.PokemonApiService

/**
 * Created by SVDroid on 6/30/21.
 */

fun PokemonApiService.PokemonListItemResponse.toDBEntity(id: Long): PokemonListEntity =
        PokemonListEntity(
                id = id,
                name = name,
                url = url
        )

fun PokemonApiService.PokemonDetailsResponse.toDBEntity(): PokemonDetailsEntity =
        PokemonDetailsEntity(
                id = id,
                baseExperience = baseExperience,
                weight = weight.toFloat() / 10.toFloat(),
                height = height.toFloat() / 10.toFloat(),
                name = name,
                abilities = abilities.joinToString(separator = ", ", postfix = ".")
        )
