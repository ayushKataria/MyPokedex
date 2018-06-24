package com.example.akat2.mypokedex.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.akat2.mypokedex.R
import com.example.akat2.mypokedex.models.PokemonListItemModel
import com.example.akat2.mypokedex.utils.URL_POKEMON_IMAGE

/**
 * Created by Ayush Kataria on 21-06-2018.
 */
class PokemonListAdapter(val context: Context, val pokemonList: ArrayList<PokemonListItemModel>): RecyclerView.Adapter<PokemonListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.pokemon_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindPokemon(pokemonList[position])
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        val pokemonListImage = itemView?.findViewById<ImageView>(R.id.pokemonListImageView)
        val pokemonListNameTxt = itemView?.findViewById<TextView>(R.id.pokemonListNameTxt)

        fun bindPokemon(pokemon: PokemonListItemModel) {

            pokemonListNameTxt?.text = pokemon.name

            val pokemonUrl = pokemon.url
            val pokemonId = pokemonUrl.replaceFirst("https://pokeapi.co/api/v2/pokemon/", "").replaceFirst("/", "")
            val imageUrl = "$URL_POKEMON_IMAGE$pokemonId.png"

            if (pokemonListImage != null) {
                Glide.with(context)
                        .load(imageUrl)
                        .into(pokemonListImage)
            }
        }
    }
}