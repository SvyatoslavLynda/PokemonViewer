package com.svdroid.pokemonviewer.ui.pokemons.details

import androidx.lifecycle.MutableLiveData
import com.svdroid.pokemonviewer.api.pokemons.details.PokemonDetailsUseCase
import com.svdroid.pokemonviewer.db.PokemonDetailsDao
import com.svdroid.pokemonviewer.db.toDBEntity
import com.svdroid.pokemonviewer.utils.BaseViewModel
import com.svdroid.pokemonviewer.utils.DataState

/**
 * Created by SVDroid on 6/30/21.
 */
class PokemonDetailsViewModel(private val name: String?, private val db: PokemonDetailsDao) :
    BaseViewModel() {
    val state = MutableLiveData<DataState>()

    init {
        requestDetails()
    }

    private fun requestDetails() {
        state.value = DataState.Loading

        doWork {
            val entity = db.getByName(name)
            if (entity != null) {
                state.postValue(DataState.Data(entity))
                return@doWork
            }

            try {
                val result = PokemonDetailsUseCase()
                    .doWork(PokemonDetailsUseCase.Params(name))
                result.response?.toDBEntity()?.apply {
                    db.insertAll(this)
                    state.postValue(DataState.Data(this))
                } ?: state.postValue(DataState.Error)
            } catch (e: Exception) {
                state.postValue(DataState.Error)
            }
        }
    }
}