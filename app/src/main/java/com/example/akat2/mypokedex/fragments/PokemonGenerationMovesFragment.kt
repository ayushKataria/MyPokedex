package com.example.akat2.mypokedex.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.akat2.mypokedex.R
import com.example.akat2.mypokedex.activities.PokemonGenerationsActivity
import kotlinx.android.synthetic.main.fragment_pokemon_generation_moves.*

/**
 * Created by Ayush Kataria on 20-07-2018.
 */
class PokemonGenerationMovesFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_pokemon_generation_moves, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val generationDetails = (activity as PokemonGenerationsActivity).generationDetails

        val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, generationDetails.moves)
        generationMoveList.adapter = adapter
    }

}