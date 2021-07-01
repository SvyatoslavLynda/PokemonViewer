package com.svdroid.pokemonviewer.utils

/**
 * Created by SVDroid on 3/2/21.
 */
sealed class DataState {
    object Loading : DataState()
    class Data<M>(val data: M?) : DataState()
    object Error : DataState()
}