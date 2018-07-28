package com.example.akat2.mypokedex.fragments.pokemonDetailFragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.akat2.mypokedex.R
import com.example.akat2.mypokedex.activities.PokemonDetailActivity
import com.example.akat2.mypokedex.adapters.PokemonStatListAdapter
import kotlinx.android.synthetic.main.fragment_pokemon_detail_stats.*

/**
 * Created by Ayush Kataria on 08-07-2018.
 */
class PokemonDetailStatsFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_pokemon_detail_stats, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pokemon = (activity as PokemonDetailActivity).pokemonDetail

        pokemonDetailStatsList.adapter = PokemonStatListAdapter(context, pokemon.stats)
        pokemonDetailStatsList.layoutManager = LinearLayoutManager(context)
    }
}