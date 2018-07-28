package com.example.akat2.mypokedex.fragments.pokemonTypeFragments

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
import com.example.akat2.mypokedex.activities.PokemonTypeDetailActivity
import com.example.akat2.mypokedex.adapters.PokemonListAdapter
import com.example.akat2.mypokedex.utils.POKEMON_URL_TAG
import kotlinx.android.synthetic.main.fragment_pokemon_type_pokemons.*

/**
 * Created by Ayush Kataria on 22-07-2018.
 */
class PokemonTypePokemonsFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_pokemon_type_pokemons, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pokemonList = (activity as PokemonTypeDetailActivity).typeDetails.pokemonList

        val adapter = PokemonListAdapter(context, pokemonList) { pokemonListItemModel, imageView ->
            val pokemonDetailIntent = Intent(context, PokemonDetailActivity::class.java)
            pokemonDetailIntent.putExtra(POKEMON_URL_TAG, pokemonListItemModel.url)
            val options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(activity as PokemonTypeDetailActivity, imageView, "pokemonImage")
            startActivity(pokemonDetailIntent, options.toBundle())
        }
        pokemonTypePokeList.adapter = adapter
        pokemonTypePokeList.layoutManager = GridLayoutManager(context, 3)
    }
}