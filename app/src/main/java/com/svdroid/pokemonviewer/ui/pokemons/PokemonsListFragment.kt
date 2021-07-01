package com.svdroid.pokemonviewer.ui.pokemons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.svdroid.pokemonviewer.R
import com.svdroid.pokemonviewer.api.PokemonApiService
import com.svdroid.pokemonviewer.db.PokemonListEntity
import com.svdroid.pokemonviewer.ui.MainActivity
import com.svdroid.pokemonviewer.ui.pokemons.details.KEY_POKEMON_NAME
import com.svdroid.pokemonviewer.utils.DataState
import com.svdroid.pokemonviewer.utils.createErrorStub
import com.svdroid.pokemonviewer.utils.createLoadingStub
import kotlinx.android.synthetic.main.error_view_stub.*
import kotlinx.android.synthetic.main.fragment_pokemon_list.*
import kotlinx.android.synthetic.main.loading_view_stub.*
import kotlinx.android.synthetic.main.pokemon_item_view.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by SVDroid on 6/30/21.
 */
class PokemonsListFragment : Fragment() {
    private val viewModel by viewModel<PokemonsListViewModel>()
    private val adapter: PokemonsAdapter by lazy {
        PokemonsAdapter(LayoutInflater.from(requireContext()))
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pokemon_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObservers()
    }

    private fun initUI() {
        (requireActivity() as MainActivity).apply {
            title = getString(R.string.app_name)
            changeStateOfBackButton(false)
        }
        setHasOptionsMenu(false)
        pokemonsList.adapter = adapter
    }

    private fun initObservers() {
        viewModel.state.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Loading -> showLoading()
                is DataState.Data<*> -> showData(dataState.data as List<PokemonListEntity>)
                is DataState.Error -> showError()
            }
        })
    }

    private fun showLoading() {
        if (loadingContainer == null) {
            pokemonsListContainer.createLoadingStub()
            loadingContainer.isVisible = true
        }
        errorContainer?.isVisible = false
        pokemonsList.isVisible = false
    }

    private fun showData(data: List<PokemonListEntity>) {
        adapter.data = data
        errorContainer?.isVisible = false
        loadingContainer?.isVisible = false
        pokemonsList.isVisible = true
    }

    private fun showError() {
        loadingContainer?.isVisible = false
        if (errorContainer == null) {
            pokemonsListContainer.createErrorStub()
            errorContainer.isVisible = true
        }
        pokemonsList.isVisible = false
    }
}

private class PokemonsAdapter(private val inflater: LayoutInflater) :
        RecyclerView.Adapter<PokemonViewHolder>() {
    var data: List<PokemonListEntity> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(inflater.inflate(R.layout.pokemon_item_view, parent, false))
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

}

private class PokemonViewHolder(item: View) : RecyclerView.ViewHolder(item) {
    fun bind(pokemon: PokemonListEntity) {
        itemView.setOnClickListener {
            itemView.findNavController().navigate(
                    R.id.pokemonDetailsFragment,
                    Bundle(1).apply {
                        putString(KEY_POKEMON_NAME, pokemon.name)
                    },
                    null,
            )
        }
        itemView.nameTextView.text = pokemon.name

        Glide
                .with(itemView.context)
                .load(PokemonApiService.getPokemonPNGImageURL(pokemonId = pokemon.id))
                .centerInside()
                .placeholder(R.drawable.pic_pokeball_disabled)
                .error(R.drawable.pic_pokeball)
                .into(itemView.avatarImageView)
    }
}