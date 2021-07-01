package com.svdroid.pokemonviewer.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.svdroid.pokemonviewer.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun changeStateOfBackButton(needShow: Boolean) {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(needShow)
            setDisplayShowHomeEnabled(needShow)
        }
    }
}