package com.example.akat2.mypokedex.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.akat2.mypokedex.fragments.PokemonDetailAbilitiesFragment
import com.example.akat2.mypokedex.fragments.PokemonDetailOverviewFragment

/**
 * Created by Ayush Kataria on 02-07-2018.
 */
class PokemonDetailViewPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    val pageTitles = arrayListOf<String>("Overview", "Abilities", "Games", "Stats", "Moves")

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> PokemonDetailOverviewFragment()
            1 -> PokemonDetailAbilitiesFragment()
            else -> Fragment()
        }
    }

    override fun getCount(): Int {
        return 5
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return pageTitles[position]
    }
}
