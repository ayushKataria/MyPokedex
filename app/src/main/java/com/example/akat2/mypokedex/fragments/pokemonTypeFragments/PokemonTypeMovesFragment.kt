package com.example.akat2.mypokedex.fragments.pokemonTypeFragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.akat2.mypokedex.R
import com.example.akat2.mypokedex.activities.PokemonTypeDetailActivity
import kotlinx.android.synthetic.main.fragment_pokemon_type_moves.*

/**
 * Created by Ayush Kataria on 22-07-2018.
 */
class PokemonTypeMovesFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_pokemon_type_moves, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val typeDetails = (activity as PokemonTypeDetailActivity).typeDetails

        val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, typeDetails.moveList)
        typeMoveList.adapter = adapter
    }
}