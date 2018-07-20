package com.example.akat2.mypokedex.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.akat2.mypokedex.R
import com.example.akat2.mypokedex.activities.PokemonDetailActivity
import com.example.akat2.mypokedex.activities.PokemonGenerationsActivity
import com.example.akat2.mypokedex.adapters.PokemonListAdapter
import com.example.akat2.mypokedex.utils.POKEMON_URL_TAG
import kotlinx.android.synthetic.main.fragment_pokemon_generation_pokemons.*

/**
 * Created by Ayush Kataria on 19-07-2018.
 */
class PokemonGenerationPokemonsFragment: Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_pokemon_generation_pokemons, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pokemonList = (activity as PokemonGenerationsActivity).generationDetails.pokemons

        val adapter = PokemonListAdapter(context, pokemonList) { pokemonListItemModel, imageView ->
            val pokemonDetailIntent = Intent(context, PokemonDetailActivity::class.java)
            pokemonDetailIntent.putExtra(POKEMON_URL_TAG, pokemonListItemModel.url)
            val options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(activity as PokemonGenerationsActivity, imageView, "pokemonImage")
            startActivity(pokemonDetailIntent, options.toBundle())
        }
        pokemonGenerationPokeList.adapter = adapter
        pokemonGenerationPokeList.layoutManager = GridLayoutManager(context, 3)

    }
}