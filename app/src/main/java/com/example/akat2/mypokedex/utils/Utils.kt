package com.example.akat2.mypokedex.utils

/**
 * Created by Ayush Kataria on 27-06-2018.
 */
object Utils {

    fun getImageUrl(pokemonUrl: String): String {

        val pokemonId = pokemonUrl.replaceFirst("https://pokeapi.co/api/v2/pokemon/", "").replaceFirst("/", "")
        val imageUrl = "$URL_POKEMON_IMAGE$pokemonId.png"

        return imageUrl
    }

}