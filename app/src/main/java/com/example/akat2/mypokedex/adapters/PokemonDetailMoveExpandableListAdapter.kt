package com.example.akat2.mypokedex.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.example.akat2.mypokedex.R
import com.example.akat2.mypokedex.models.Move
import com.example.akat2.mypokedex.models.MoveVersionGroupDetails

/**
 * Created by Ayush Kataria on 04-07-2018.
 */
class PokemonDetailMoveExpandableListAdapter(val context: Context?, private val moves: ArrayList<Move>): BaseExpandableListAdapter() {

    override fun getGroup(groupPosition: Int): Any {
        return moves[groupPosition].name
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return false
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val headerTitle = getGroup(groupPosition).toString()
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.pokemon_move_list_group, parent, false)

        val pokemonDetailGameTextView = view.findViewById<TextView>(R.id.pokemonDetailGameTxt)
        pokemonDetailGameTextView.text = headerTitle

        return view
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return moves[groupPosition].versionGroups.size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return moves[groupPosition].versionGroups[childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val versionGroupDetails = getChild(groupPosition, childPosition) as MoveVersionGroupDetails
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.pokemon_move_list_item, parent, false)

        val pokemonDetailMoveVersionNameTxt = view.findViewById<TextView>(R.id.pokemonDetailMoveVersionNameTxt)
        val pokemonDetailMoveVersionLevelLearnedAtTxt = view.findViewById<TextView>(R.id.pokemonDetailMoveVersionLevelLearnedAtTxt)
        val pokemonDetailMoveVersionLearnMethodTxt = view.findViewById<TextView>(R.id.pokemonDetailMoveVersionLearnMethodTxt)

        pokemonDetailMoveVersionNameTxt.text = versionGroupDetails.versionGroup
        pokemonDetailMoveVersionLevelLearnedAtTxt.text = versionGroupDetails.levelLearnedAt.toString()
        pokemonDetailMoveVersionLearnMethodTxt.text = versionGroupDetails.learnMethod

        return view
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return moves.size
    }
}