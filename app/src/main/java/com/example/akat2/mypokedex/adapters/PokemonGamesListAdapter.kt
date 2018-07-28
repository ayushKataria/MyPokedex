package com.example.akat2.mypokedex.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.akat2.mypokedex.R
import com.example.akat2.mypokedex.utils.Utils

/**
 * Created by Ayush Kataria on 08-07-2018.
 */
class PokemonGamesListAdapter(val context: Context?, val gameList: ArrayList<String>,
                              val itemClick: (String) -> Unit): RecyclerView.Adapter<PokemonGamesListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.pokemon_game_list_item, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return gameList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindGame(gameList[position])
    }


    inner class ViewHolder(itemView: View?, val itemClick: (String) -> Unit) : RecyclerView.ViewHolder(itemView) {

        val pokemonGameNameTxt = itemView?.findViewById<TextView>(R.id.pokemonDetailGameNameTxt)

        fun bindGame(gameName: String) {

            pokemonGameNameTxt?.text = Utils.formatString(gameName)

            itemView?.setOnClickListener { itemClick(gameName) }
        }

    }
}