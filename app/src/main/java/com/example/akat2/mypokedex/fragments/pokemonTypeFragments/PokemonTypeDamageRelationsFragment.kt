package com.example.akat2.mypokedex.fragments.pokemonTypeFragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.akat2.mypokedex.R
import com.example.akat2.mypokedex.activities.PokemonTypeDetailActivity
import com.example.akat2.mypokedex.adapters.PokemonTypeListAdapter
import kotlinx.android.synthetic.main.fragment_pokemon_type_damage_relations.*

/**
 * Created by Ayush Kataria on 22-07-2018.
 */
class PokemonTypeDamageRelationsFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_pokemon_type_damage_relations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val typeDetails = (activity as PokemonTypeDetailActivity).typeDetails

        typeNoDamageToList.adapter = PokemonTypeListAdapter(context, typeDetails.noDamageTo, null)
        typeNoDamageToList.layoutManager = LinearLayoutManager(context)

        typeHalfDamageToList.adapter = PokemonTypeListAdapter(context, typeDetails.halfDamageTo, null)
        typeHalfDamageToList.layoutManager = LinearLayoutManager(context)

        typeDoubleDamageToList.adapter = PokemonTypeListAdapter(context, typeDetails.doubleDamageTo, null)
        typeDoubleDamageToList.layoutManager = LinearLayoutManager(context)

        typeNoDamageFromList.adapter = PokemonTypeListAdapter(context, typeDetails.noDamageFrom, null)
        typeNoDamageFromList.layoutManager = LinearLayoutManager(context)

        typeHalfDamageFromList.adapter = PokemonTypeListAdapter(context, typeDetails.halfDamageFrom, null)
        typeHalfDamageFromList.layoutManager = LinearLayoutManager(context)

        typeDoubleDamageFromList.adapter = PokemonTypeListAdapter(context, typeDetails.doubleDamageFrom, null)
        typeDoubleDamageFromList.layoutManager = LinearLayoutManager(context)
    }
}