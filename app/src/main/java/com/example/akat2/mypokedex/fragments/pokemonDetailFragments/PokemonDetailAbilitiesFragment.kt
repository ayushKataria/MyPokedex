package com.example.akat2.mypokedex.fragments.pokemonDetailFragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.akat2.mypokedex.R
import com.example.akat2.mypokedex.activities.PokemonDetailActivity
import com.example.akat2.mypokedex.adapters.PokemonAbilityListAdapter
import kotlinx.android.synthetic.main.fragment_pokemon_detail_abilities.*

/**
 * Created by Ayush Kataria on 03-07-2018.
 */
class PokemonDetailAbilitiesFragment: Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_pokemon_detail_abilities, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pokemon = (activity as PokemonDetailActivity).pokemonDetail

        pokemonDetailAbilitiesList.adapter = PokemonAbilityListAdapter(context, pokemon.abilities) {
            //TODO: handle item clicks
        }

        pokemonDetailAbilitiesList.layoutManager = LinearLayoutManager(context)
    }
}