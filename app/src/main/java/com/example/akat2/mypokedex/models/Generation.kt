package com.example.akat2.mypokedex.models

/**
 * Created by Ayush Kataria on 24-06-2018.
 */
class Generation(val id: Int, val name: String, val abilities: ArrayList<Ability>, val mainRegion: Region,
                 val moves: ArrayList<Move>, val pokemonSpecies: ArrayList<PokemonSpecies>,
                 val versionGroups: ArrayList<GameVersionGroup>) {
}