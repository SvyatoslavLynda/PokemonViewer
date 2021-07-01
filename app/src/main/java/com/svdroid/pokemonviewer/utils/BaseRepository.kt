package com.svdroid.pokemonviewer.utils

/**
 * Created by SVDroid on 2/26/20.
 */
abstract class BaseRepository<Params, Result> {

    abstract suspend fun doWork(params: Params): Result
}