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
import com.example.akat2.mypokedex.models.TypesListItemModel
import com.example.akat2.mypokedex.utils.Utils

/**
 * Created by Ayush Kataria on 21-07-2018.
 */
class PokemonTypeGridAdapter(val context: Context, val typeList: ArrayList<TypesListItemModel>,
                             val itemClick: (TypesListItemModel) -> Unit): RecyclerView.Adapter<PokemonTypeGridAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.pokemon_list_item, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return typeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindType(typeList[position])
    }

    inner class ViewHolder(itemView: View?, val itemClick: (TypesListItemModel) -> Unit) : RecyclerView.ViewHolder(itemView) {

        private val typeListImage = itemView?.findViewById<ImageView>(R.id.pokemonListImageView)
        private val typeListNameTxt = itemView?.findViewById<TextView>(R.id.pokemonListNameTxt)

        fun bindType(type: TypesListItemModel) {

            typeListNameTxt?.text = Utils.formatString(type.name)

            val imageResource = Utils.getTypeImageResourceId(context, type.name)

            if (imageResource != null) {
                typeListImage?.setImageResource(imageResource)
            }

            itemView?.setOnClickListener {
                itemClick(type)
            }
        }
    }
}