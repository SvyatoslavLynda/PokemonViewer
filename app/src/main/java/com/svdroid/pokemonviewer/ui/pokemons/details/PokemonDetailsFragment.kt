package com.svdroid.pokemonviewer.ui.pokemons.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.svdroid.pokemonviewer.R
import com.svdroid.pokemonviewer.api.PokemonApiService
import com.svdroid.pokemonviewer.db.PokemonDetailsEntity
import com.svdroid.pokemonviewer.ui.MainActivity
import com.svdroid.pokemonviewer.utils.DataState
import com.svdroid.pokemonviewer.utils.createErrorStub
import com.svdroid.pokemonviewer.utils.createLoadingStub
import kotlinx.android.synthetic.main.error_view_stub.*
import kotlinx.android.synthetic.main.fragment_pokemon_details.*
import kotlinx.android.synthetic.main.loading_view_stub.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


/**
 * Created by SVDroid on 6/30/21.
 */

const val KEY_POKEMON_NAME = ".KEY_POKEMON_NAME"

class PokemonDetailsFragment : Fragment() {
    private val viewModel by viewModel<PokemonDetailsViewModel> {
        parametersOf(requireArguments().getString(KEY_POKEMON_NAME, null))
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pokemon_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObservers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            requireActivity().onBackPressed()

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun initUI() {
        setHasOptionsMenu(true)
        (requireActivity() as MainActivity).changeStateOfBackButton(true)
    }

    private fun initObservers() {
        viewModel.state.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Loading -> showLoading()
                is DataState.Data<*> -> showData(dataState.data as PokemonDetailsEntity)
                is DataState.Error -> showError()
            }
        })
    }

    private fun showLoading() {
        if (loadingContainer == null) {
            pokemonDetailsContainer.createLoadingStub()
            loadingContainer.isVisible = true
        }
        errorContainer?.isVisible = false
        dataContainer.isVisible = false
    }

    private fun showData(data: PokemonDetailsEntity) {
        loadingContainer?.isVisible = false
        errorContainer?.isVisible = false
        dataContainer.isVisible = true
        (requireActivity() as MainActivity).title = data.name
        baseExperienceTextView.text = data.baseExperience.toString()
        weightTextView.text = getString(R.string.weight_pattern, data.weight)
        heightTextView.text = getString(R.string.height_pattern, data.height)
        abilitiesTextView.text = data.abilities
        Glide
                .with(requireContext())
                .load(PokemonApiService.getPokemonPNGImageURL(pokemonId = data.id))
                .centerInside()
                .placeholder(R.drawable.pic_pokeball_disabled)
                .error(R.drawable.pic_pokeball)
                .into(avatarImageView)
    }

    private fun showError() {
        loadingContainer?.isVisible = false
        if (errorContainer == null) {
            pokemonDetailsContainer.createErrorStub()
            errorContainer.isVisible = true
        }
        dataContainer.isVisible = false
    }
}