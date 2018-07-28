package com.example.akat2.mypokedex.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.akat2.mypokedex.R
import com.example.akat2.mypokedex.models.Ability
import com.example.akat2.mypokedex.utils.Utils

/**
 * Created by Ayush Kataria on 03-07-2018.
 */
class PokemonAbilityListAdapter(val context: Context?, private val abilityList: ArrayList<Ability>,
                                val itemClick: (Ability) -> Unit): RecyclerView.Adapter<PokemonAbilityListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.pokemon_ability_list_item, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return abilityList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindAbility(abilityList[position])
    }


    inner class ViewHolder(itemView: View?, val itemClick: (Ability) -> Unit) : RecyclerView.ViewHolder(itemView) {

        val abilityNameTxt = itemView?.findViewById<TextView>(R.id.pokemonAbilityNameTxt)
        val abilitySlotTxt = itemView?.findViewById<TextView>(R.id.pokemonAbilitySlotTxt)
        val abilityHiddenTxt = itemView?.findViewById<TextView>(R.id.pokemonAbilityIsHiddenTxt)

        fun bindAbility(ability: Ability) {

            abilityNameTxt?.text = Utils.formatString(ability.name)
            abilitySlotTxt?.text = ability.slot.toString()

            if(ability.isHidden){
                abilityHiddenTxt?.visibility = View.VISIBLE
            }else {
                abilityHiddenTxt?.visibility = View.GONE
            }

            itemView?.setOnClickListener { itemClick(ability) }

        }

    }
}