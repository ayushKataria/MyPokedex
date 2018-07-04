package com.example.akat2.mypokedex.models

/**
 * Created by Ayush Kataria on 25-06-2018.
 */
class Pokemon(var id: Int, var name: String, var baseExperience: Int, var height: Int, var weight: Int,
              val abilities: ArrayList<Ability>, val gameVersionNames: ArrayList<String>, val gameVersionUrls: HashMap<String, String>,
              val types: ArrayList<String>, val typeUrls: HashMap<String, String>, val stats: ArrayList<PokemonStat>,
              val sprites: HashMap<String, String>, val moves: ArrayList<Move>) {

}
