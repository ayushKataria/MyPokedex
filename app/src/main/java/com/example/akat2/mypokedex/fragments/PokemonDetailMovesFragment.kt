package com.example.akat2.mypokedex.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.akat2.mypokedex.R
import com.example.akat2.mypokedex.activities.PokemonDetailActivity
import com.example.akat2.mypokedex.adapters.PokemonDetailMoveExpandableListAdapter
import kotlinx.android.synthetic.main.fragment_pokemon_details_moves.*

/**
 * Created by Ayush Kataria on 04-07-2018.
 */
class PokemonDetailMovesFragment: Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_pokemon_details_moves, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pokemon = (activity as PokemonDetailActivity).pokemonDetail

        pokemonDetailMovesList.setAdapter(PokemonDetailMoveExpandableListAdapter(context, pokemon.moves))
    }
}