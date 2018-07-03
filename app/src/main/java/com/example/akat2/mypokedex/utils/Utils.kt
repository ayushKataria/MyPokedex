package com.example.akat2.mypokedex.utils

import android.content.Context
import android.graphics.BitmapFactory
import com.example.akat2.mypokedex.R

/**
 * Created by Ayush Kataria on 27-06-2018.
 */
object Utils {

    fun getImageUrl(pokemonUrl: String): String {

        val pokemonId = pokemonUrl.replaceFirst("https://pokeapi.co/api/v2/pokemon/", "").replaceFirst("/", "")
        val imageUrl = "$URL_POKEMON_IMAGE$pokemonId.png"

        return imageUrl
    }


    fun getTypeImageResourceId(context: Context?, pokemonType: String):Int? {
        val resourceId = context?.resources?.getIdentifier(pokemonType, "mipmap", context?.packageName)
        return resourceId
    }
}