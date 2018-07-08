package com.example.akat2.mypokedex.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.akat2.mypokedex.R
import com.example.akat2.mypokedex.models.PokemonStat

/**
 * Created by Ayush Kataria on 08-07-2018.
 */
class PokemonStatListAdapter(val context: Context?, val pokemonStats: ArrayList<PokemonStat>): RecyclerView.Adapter<PokemonStatListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.pokemon_stats_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pokemonStats.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindStat(pokemonStats[position])
    }


    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        val pokemonDetailStatNameTxt = itemView?.findViewById<TextView>(R.id.pokemonDetailStatNameTxt)
        val pokemonDetailStatBaseStatTxt = itemView?.findViewById<TextView>(R.id.pokemonDetailStatBaseStatTxt)
        val pokemonDetailStatEffortTxt = itemView?.findViewById<TextView>(R.id.pokemonDetailStatEffortTxt)

        fun bindStat(pokemonStat: PokemonStat) {
            pokemonDetailStatNameTxt?.text = pokemonStat.name
            pokemonDetailStatBaseStatTxt?.text = pokemonStat.baseStat.toString()
            pokemonDetailStatEffortTxt?.text = pokemonStat.effort.toString()
        }


    }

}