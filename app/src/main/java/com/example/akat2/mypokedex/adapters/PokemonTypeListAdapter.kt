package com.example.akat2.mypokedex.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.akat2.mypokedex.R
import com.example.akat2.mypokedex.utils.Utils

/**
 * Created by Ayush Kataria on 02-07-2018.
 */
class PokemonTypeListAdapter(val context: Context?, val types: ArrayList<String>, val typeUrl: HashMap<String, String>
                             ,val itemClick: (String) -> Unit): RecyclerView.Adapter<PokemonTypeListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.pokemon_type_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return types.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindType(types[position])
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView){

        val typeNameTextView = itemView?.findViewById<TextView>(R.id.pokemonListTypeTxt)
        val typeImageView = itemView?.findViewById<ImageView>(R.id.pokemonTypeImageView)

        fun bindType(typeName: String) {
            typeNameTextView?.text = typeName

            typeImageView?.setImageResource(Utils.getTypeImageResourceId(context, typeName)!!)

            itemView.setOnClickListener { itemClick(typeUrl[typeName]!!) }
        }
    }

}