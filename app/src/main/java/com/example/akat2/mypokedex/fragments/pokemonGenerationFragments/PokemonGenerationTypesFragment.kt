package com.example.akat2.mypokedex.fragments.pokemonGenerationFragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.akat2.mypokedex.R
import com.example.akat2.mypokedex.activities.PokemonGenerationsActivity
import com.example.akat2.mypokedex.adapters.PokemonTypeListAdapter
import kotlinx.android.synthetic.main.fragment_pokemon_generation_types.*

/**
 * Created by Ayush Kataria on 20-07-2018.
 */
class PokemonGenerationTypesFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_pokemon_generation_types, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val generationDetails = (activity as PokemonGenerationsActivity).generationDetails

        if(generationDetails.types.size > 0) {
            generationNoTypesTxt.visibility = View.GONE
            generationTypesList.adapter = PokemonTypeListAdapter(context, generationDetails.types, generationDetails.typeUrls)
            generationTypesList.layoutManager = LinearLayoutManager(context)
        }else {
            generationNoTypesTxt.visibility = View.VISIBLE
        }
    }
}