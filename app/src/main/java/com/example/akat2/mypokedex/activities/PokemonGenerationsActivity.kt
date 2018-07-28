package com.example.akat2.mypokedex.activities

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.akat2.mypokedex.App
import com.example.akat2.mypokedex.R
import com.example.akat2.mypokedex.fragments.pokemonGenerationFragments.PokemonGenerationMovesFragment
import com.example.akat2.mypokedex.fragments.pokemonGenerationFragments.PokemonGenerationPokemonsFragment
import com.example.akat2.mypokedex.fragments.pokemonGenerationFragments.PokemonGenerationTypesFragment
import com.example.akat2.mypokedex.models.Generation
import com.example.akat2.mypokedex.models.PokemonListItemModel
import com.example.akat2.mypokedex.utils.BASE_URL
import com.example.akat2.mypokedex.utils.URL_GENERATION
import com.example.akat2.mypokedex.utils.Utils
import kotlinx.android.synthetic.main.activity_pokemon_generations.*
import org.json.JSONException

class PokemonGenerationsActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    val generationNames: ArrayList<String> = ArrayList()
    val generationUrls: HashMap<String, String> = HashMap()
    lateinit var generationDetails: Generation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_generations)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu_white)
        }

        progressBar.indeterminateDrawable.setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY)

        genNavView.setNavigationItemSelectedListener { item: MenuItem ->
            //handle menu item clicks
            val id = item.itemId
            when(id){
                R.id.navViewPokemon -> {
                    val pokemonIntent = Intent(this, PokemonListActivity::class.java)
                    pokemonIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(pokemonIntent)
                    finish()
                }
                R.id.navViewGeneration -> {
                    //Already here
                }
                R.id.navViewType -> {
                    val typeIntent = Intent(this, PokemonTypeListActivity::class.java)
                    typeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(typeIntent)
                    finish()
                }
                R.id.navViewBerry -> {
                    val berryIntent = Intent(this, PokemonBerryListActivity::class.java)
                    berryIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(berryIntent)
                    finish()
                }
            }
            true
        }

        loadGenerationList()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            android.R.id.home -> {
                genDrawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun generationListReceived() {

        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, generationNames)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        generationSpinner.adapter = spinnerAdapter

        generationSpinner.onItemSelectedListener = this
    }

    private fun loadGenerationList() {

        val url = "$BASE_URL$URL_GENERATION"
        progressBar.visibility = View.VISIBLE

        val generationListRequest = object : JsonObjectRequest(Method.GET, url, null, Response.Listener { response ->
            //Request Success
            generationNames.clear()
            generationUrls.clear()
            try {
                val resultJsonArray = response.getJSONArray("results")
                for (i in 0 until resultJsonArray.length()){
                    val jsonObject = resultJsonArray.getJSONObject(i)

                    val generationName = jsonObject.getString("name")
                    val generationUrl = jsonObject.getString("url")

                    generationNames.add(Utils.formatString(generationName))
                    generationUrls[Utils.formatString(generationName)] = generationUrl
                }
                generationListReceived()
            }catch (e: JSONException){
                progressBar.visibility = View.GONE
                Toast.makeText(this, e.localizedMessage, Toast.LENGTH_LONG).show()
                Log.d("EXC", e.localizedMessage)
            }
        }, Response.ErrorListener { error ->
            //Request failed
            progressBar.visibility = View.GONE
            Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
            error.printStackTrace()
        }){}

        App.requestQueue.add(generationListRequest)
    }

    fun generationDataLoaded() {

        loadFragment(PokemonGenerationPokemonsFragment())

        generationBottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->

            var fragment: Fragment? = null

            when(item.itemId){
                R.id.genBottomNavPokemon -> {
                    fragment = PokemonGenerationPokemonsFragment()
                }
                R.id.genBottomNavMoves -> {
                    fragment = PokemonGenerationMovesFragment()
                }
                R.id.genBottomNavTypes -> {
                    fragment = PokemonGenerationTypesFragment()
                }
            }
            return@setOnNavigationItemSelectedListener loadFragment(fragment)
        }
    }

    private fun loadGenerationData(url: String?) {

        progressBar.visibility = View.VISIBLE
        generationDetails = Generation(0, "", ArrayList(), HashMap(), ArrayList(), ArrayList())

        val generationDetailsRequest = object : JsonObjectRequest(Method.GET, url, null, Response.Listener { response ->
            //Request Success
            progressBar.visibility = View.GONE
            try {
                generationDetails.id = response.getInt("id")
                generationDetails.name = response.getString("name")

                val typeJsonArray = response.getJSONArray("types")

                for(i in 0 until typeJsonArray.length()){
                    val typeJsonObject = typeJsonArray.getJSONObject(i)
                    generationDetails.types.add(typeJsonObject.getString("name"))
                    generationDetails.typeUrls[typeJsonObject.getString("name")] = typeJsonObject.getString("url")
                }

                val movesJsonArray = response.getJSONArray("moves")

                for (i in 0 until movesJsonArray.length()) {
                    val moveJsonObject = movesJsonArray.getJSONObject(i)
                    val name = moveJsonObject.getString("name")
                    generationDetails.moves.add(Utils.formatString(name))
                }

                val pokemonJsonArray = response.getJSONArray("pokemon_species")

                for(i in 0 until pokemonJsonArray.length()){
                    val pokemonJsonObject = pokemonJsonArray.getJSONObject(i)
                    val name = pokemonJsonObject.getString("name")
                    val url = pokemonJsonObject.getString("url").replaceFirst("https://pokeapi.co/api/v2/pokemon-species/"
                            , "https://pokeapi.co/api/v2/pokemon/")
                    generationDetails.pokemons.add(PokemonListItemModel(name, url))
                }
                generationDataLoaded()

            }catch (e: JSONException){
                Toast.makeText(this, e.localizedMessage, Toast.LENGTH_LONG).show()
                Log.d("EXC", e.localizedMessage)
            }
        }, Response.ErrorListener { error ->
            //Request failed
            progressBar.visibility = View.GONE
            Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
            error.printStackTrace()
        }){}

        App.requestQueue.add(generationDetailsRequest)
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if(fragment != null){
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.pokemonGenFragmentContainer, fragment)
                    .commit()
            return true
        }
        return false
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        //Do nothing
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        loadGenerationData(generationUrls[generationNames[position]])
    }
}
