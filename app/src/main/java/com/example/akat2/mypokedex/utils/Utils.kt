package com.example.akat2.mypokedex.utils

import android.content.Context

/**
 * Created by Ayush Kataria on 27-06-2018.
 */
object Utils {

    fun getPokemonImageUrl(pokemonUrl: String): String {
        val pokemonId = pokemonUrl.replaceFirst("https://pokeapi.co/api/v2/pokemon/", "").replaceFirst("/", "")
        return "$URL_POKEMON_IMAGE$pokemonId.png"
    }

    fun getBerryImageUrl(berryName: String): String {
        return "$URL_BERRY_IMAGE$berryName-berry.png"
    }

    fun getTypeImageResourceId(context: Context?, pokemonType: String):Int? {
        return context?.resources?.getIdentifier(pokemonType, "mipmap", context.packageName)
    }

    fun formatString(string: String?): String {

        val stringArray = string?.split("-")
        var formattedString = ""
        if (stringArray != null) {
            for(s in stringArray) {
                if(s.isNotEmpty())
                    formattedString = "$formattedString${s.substring(0, 1).toUpperCase()}${s.substring(1)} "
            }
        }
        return formattedString.trim()
    }
}