package com.svdroid.pokemonviewer.db

import androidx.room.*

/**
 * Created by SVDroid on 6/30/21.
 */

@Database(entities = [PokemonListEntity::class, PokemonDetailsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonDetailsDao(): PokemonDetailsDao
    abstract fun pokemonListDao(): PokemonListDao
}

@Dao
interface PokemonDetailsDao {
    @Query("SELECT * FROM PokemonDetailsEntity WHERE name == (:name)")
    fun getByName(name: String?): PokemonDetailsEntity?

    @Insert
    fun insertAll(vararg pokemons: PokemonDetailsEntity)
}

@Entity
data class PokemonDetailsEntity(
        @PrimaryKey val id: Long,
        @ColumnInfo(name = "base_experience") val baseExperience: Int,
        @ColumnInfo(name = "weight") val weight: Float,
        @ColumnInfo(name = "height") val height: Float,
        @ColumnInfo(name = "name") val name: String?,
        @ColumnInfo(name = "abilities") val abilities: String?,
)

@Dao
interface PokemonListDao {
    @Query("SELECT * FROM PokemonListEntity ORDER BY name ASC")
    fun getAll(): List<PokemonListEntity>

    @Insert
    fun insertAll(pokemons: List<PokemonListEntity>)
}

@Entity
data class PokemonListEntity(
        @PrimaryKey val id: Long,
        @ColumnInfo(name = "name") val name: String?,
        @ColumnInfo(name = "url") val url: String?
)