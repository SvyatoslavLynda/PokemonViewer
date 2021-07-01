package com.svdroid.pokemonviewer.di

import android.app.Application
import androidx.room.Room
import com.svdroid.pokemonviewer.db.AppDatabase
import com.svdroid.pokemonviewer.db.PokemonDetailsDao
import com.svdroid.pokemonviewer.db.PokemonListDao
import com.svdroid.pokemonviewer.ui.pokemons.PokemonsListViewModel
import com.svdroid.pokemonviewer.ui.pokemons.details.PokemonDetailsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by SVDroid on 6/30/21.
 */

val databaseModule = module {

    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "pokemons").build()
    }

    fun providePokemonDetailsDao(database: AppDatabase): PokemonDetailsDao {
        return database.pokemonDetailsDao()
    }

    fun providePokemonListDao(database: AppDatabase): PokemonListDao {
        return database.pokemonListDao()
    }

    single { provideDatabase(androidApplication()) }
    single { providePokemonDetailsDao(get()) }
    single { providePokemonListDao(get()) }
}

val appModule = module {
    viewModel { PokemonsListViewModel(db = get()) }
    viewModel { (name: String?) -> PokemonDetailsViewModel(name = name, db = get()) }
}

val allModules = listOf(appModule, databaseModule)