package com.example.akat2.mypokedex.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.akat2.mypokedex.R
import com.example.akat2.mypokedex.activities.PokemonDetailActivity
import com.example.akat2.mypokedex.adapters.PokemonTypeListAdapter
import kotlinx.android.synthetic.main.fragment_pokemon_detail_overview.*

/**
 * Created by Ayush Kataria on 01-07-2018.
 */
class PokemonDetailOverviewFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_pokemon_detail_overview, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pokemon = (activity as PokemonDetailActivity).pokemonDetail

        pokemonOverviewNameTxt.text = pokemon.name
        pokemonOverviewWeightTxt.text = "${((pokemon.weight.toDouble())/10).toString()}kg"
        pokemonOverviewHeightTxt.text = "${((pokemon.height.toDouble())/10).toString()}m"
        pokemonOverviewExpTxt.text = pokemon.baseExperience.toString()

        pokemonOverviewTypesList.adapter = PokemonTypeListAdapter(context, pokemon.types, pokemon.typeUrls){
            //TODO: Handle click
        }
        pokemonOverviewTypesList.layoutManager = LinearLayoutManager(context)
    }
}