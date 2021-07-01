package com.svdroid.pokemonviewer.ui.pokemons

import androidx.lifecycle.MutableLiveData
import com.svdroid.pokemonviewer.api.pokemons.PokemonsListUseCase
import com.svdroid.pokemonviewer.db.PokemonListDao
import com.svdroid.pokemonviewer.db.PokemonListEntity
import com.svdroid.pokemonviewer.db.toDBEntity
import com.svdroid.pokemonviewer.utils.BaseViewModel
import com.svdroid.pokemonviewer.utils.DataState

/**
 * Created by SVDroid on 6/30/21.
 */
class PokemonsListViewModel(private val db: PokemonListDao) : BaseViewModel() {
    val state = MutableLiveData<DataState>()

    init {
        requestPokemons()
    }

    private fun requestPokemons() {
        state.value = DataState.Loading

        doWork {
            val entities = db.getAll()

            if (!entities.isNullOrEmpty()) {
                state.postValue(DataState.Data(entities))
                return@doWork
            }

            try {
                val result = PokemonsListUseCase()
                        .doWork(PokemonsListUseCase.Params())
                val list: List<PokemonListEntity>? = result.response?.results?.mapIndexed { i, m ->
                    m.toDBEntity(i.toLong() + 1)
                }?.sortedBy { it.name }


                list?.apply {
                    db.insertAll(this)
                    state.postValue(DataState.Data(this))
                } ?: state.postValue(DataState.Error)
            } catch (e: Exception) {
                state.postValue(DataState.Error)
            }
        }
    }
}