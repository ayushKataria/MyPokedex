package com.example.akat2.mypokedex.models

/**
 * Created by Ayush Kataria on 24-06-2018.
 */
class Generation(var id: Int, var name: String, val types: ArrayList<String>, val typeUrls: HashMap<String, String>,
                 val moves: ArrayList<String>, val pokemons: ArrayList<PokemonListItemModel>) {
}