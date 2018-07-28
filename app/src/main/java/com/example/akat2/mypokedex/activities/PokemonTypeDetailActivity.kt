package com.example.akat2.mypokedex.activities

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.akat2.mypokedex.App
import com.example.akat2.mypokedex.R
import com.example.akat2.mypokedex.fragments.pokemonTypeFragments.PokemonTypeDamageRelationsFragment
import com.example.akat2.mypokedex.fragments.pokemonTypeFragments.PokemonTypeMovesFragment
import com.example.akat2.mypokedex.fragments.pokemonTypeFragments.PokemonTypePokemonsFragment
import com.example.akat2.mypokedex.models.PokemonListItemModel
import com.example.akat2.mypokedex.models.Type
import com.example.akat2.mypokedex.utils.TYPE_NAME_TAG
import com.example.akat2.mypokedex.utils.TYPE_URL_TAG
import com.example.akat2.mypokedex.utils.Utils
import kotlinx.android.synthetic.main.activity_pokemon_type_detail.*
import org.json.JSONException

class PokemonTypeDetailActivity : AppCompatActivity() {

    var typeName: String? = null
    var typeUrl: String? = null
    var typeDetails = Type(0, "", ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_type_detail)
        setSupportActionBar(typeDetailToolbar)

        typeDetailProgressBar.indeterminateDrawable.setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY)
        typeDetailProgressBar.visibility = View.VISIBLE

        typeName = intent.getStringExtra(TYPE_NAME_TAG)
        typeUrl = intent.getStringExtra(TYPE_URL_TAG)

        supportActionBar?.apply {
            title = Utils.formatString(typeName)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back_white)
        }

        loadTypeData()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun typeDataLoaded() {

        loadFragment(PokemonTypePokemonsFragment())

        typeDetailsBottomNav.setOnNavigationItemSelectedListener { item: MenuItem ->
            var fragment: Fragment? = null
            when(item.itemId){
                R.id.typeBottomNavPokemon -> fragment = PokemonTypePokemonsFragment()
                R.id.typeBottomNavMoves -> fragment = PokemonTypeMovesFragment()
                R.id.typeBottomNavDamage -> fragment = PokemonTypeDamageRelationsFragment()
            }
            loadFragment(fragment)
        }
    }

    private fun loadTypeData() {

        val typeDetailRequest = object : JsonObjectRequest(Method.GET, typeUrl, null, Response.Listener { response ->

            typeDetailProgressBar.visibility = View.GONE

            try {

                typeDetails.id = response.getInt("id")
                typeDetails.name = response.getString("name")

                val damageRelationJsonObject = response.getJSONObject("damage_relations")
                val noDamageToJsonArray = damageRelationJsonObject.getJSONArray("no_damage_to")
                val halfDamageToJsonArray = damageRelationJsonObject.getJSONArray("half_damage_to")
                val doubleDamageToJsonArray = damageRelationJsonObject.getJSONArray("double_damage_to")
                val noDamageFromJsonArray = damageRelationJsonObject.getJSONArray("no_damage_from")
                val halfDamageFromJsonArray = damageRelationJsonObject.getJSONArray("half_damage_from")
                val doubleDamageFromJsonArray = damageRelationJsonObject.getJSONArray("double_damage_from")

                for(i in 0 until noDamageToJsonArray.length())
                    typeDetails.noDamageTo.add(noDamageToJsonArray.getJSONObject(i).getString("name"))
                for(i in 0 until halfDamageToJsonArray.length())
                    typeDetails.halfDamageTo.add(halfDamageToJsonArray.getJSONObject(i).getString("name"))
                for(i in 0 until doubleDamageToJsonArray.length())
                    typeDetails.doubleDamageTo.add(doubleDamageToJsonArray.getJSONObject(i).getString("name"))
                for(i in 0 until noDamageFromJsonArray.length())
                    typeDetails.noDamageFrom.add(noDamageFromJsonArray.getJSONObject(i).getString("name"))
                for(i in 0 until halfDamageFromJsonArray.length())
                    typeDetails.halfDamageFrom.add(halfDamageFromJsonArray.getJSONObject(i).getString("name"))
                for(i in 0 until doubleDamageFromJsonArray.length())
                    typeDetails.doubleDamageFrom.add(doubleDamageFromJsonArray.getJSONObject(i).getString("name"))

                val moveJsonArray = response.getJSONArray("moves")
                for(i in 0 until moveJsonArray.length()) {
                    val name = moveJsonArray.getJSONObject(i).getString("name")
                    typeDetails.moveList.add(Utils.formatString(name))
                }
                val pokemonJsonArray = response.getJSONArray("pokemon")
                for(i in 0 until pokemonJsonArray.length()) {
                    val pokemonJsonObject = pokemonJsonArray.getJSONObject(i).getJSONObject("pokemon")
                    val name = pokemonJsonObject.getString("name")
                    val url = pokemonJsonObject.getString("url")
                    typeDetails.pokemonList.add(PokemonListItemModel(name, url))
                }
                typeDataLoaded()
            }catch (e: JSONException){
                Toast.makeText(this, e.localizedMessage, Toast.LENGTH_LONG).show()
                Log.d("EXC", e.localizedMessage)
            }

        }, Response.ErrorListener { error ->
            //Request failed
            typeDetailProgressBar.visibility = View.GONE
            Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
            error.printStackTrace()
        }){}

        App.requestQueue.add(typeDetailRequest)
    }

    fun loadFragment(fragment: Fragment?): Boolean {

        if(fragment != null){
            supportFragmentManager
                    .beginTransaction()
                    .replace(typeDetailsFragmentContainer.id, fragment)
                    .commit()
            return true
        }
        return false
    }
}
