package com.example.akat2.mypokedex.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.akat2.mypokedex.R
import com.example.akat2.mypokedex.activities.PokemonDetailActivity
import com.example.akat2.mypokedex.adapters.PokemonGamesListAdapter
import kotlinx.android.synthetic.main.fragment_pokemon_detail_games.*

/**
 * Created by Ayush Kataria on 08-07-2018.
 */
class PokemonDetailGameFragment: Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_pokemon_detail_games, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pokemon = (activity as PokemonDetailActivity).pokemonDetail

        val adapter =  PokemonGamesListAdapter(context, pokemon.gameVersionNames){ pokemonGameName ->
            //TODO: Add on click listener
        }

        pokemonDetailGamesList.adapter = adapter
        pokemonDetailGamesList.layoutManager = LinearLayoutManager(context)
    }

}