package com.example.akat2.mypokedex.models

/**
 * Created by Ayush Kataria on 22-07-2018.
 */
class Type(var id: Int, var name: String, val noDamageTo: ArrayList<String>, val halfDamageTo: ArrayList<String>,
           val doubleDamageTo: ArrayList<String>, val noDamageFrom: ArrayList<String>, val halfDamageFrom: ArrayList<String>,
           val doubleDamageFrom: ArrayList<String>, val pokemonList: ArrayList<PokemonListItemModel>, val moveList: ArrayList<String>) {
}