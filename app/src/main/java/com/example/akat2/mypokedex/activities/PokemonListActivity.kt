package com.example.akat2.mypokedex.activities

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.GravityCompat
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.akat2.mypokedex.App
import com.example.akat2.mypokedex.R
import com.example.akat2.mypokedex.adapters.PokemonListAdapter
import com.example.akat2.mypokedex.models.PokemonListItemModel
import com.example.akat2.mypokedex.utils.BASE_URL
import com.example.akat2.mypokedex.utils.POKEMON_URL_TAG
import com.example.akat2.mypokedex.utils.URL_POKEMON
import kotlinx.android.synthetic.main.activity_pokemon_list.*
import kotlinx.android.synthetic.main.pokemon_list_item.*
import org.json.JSONException

class PokemonListActivity : AppCompatActivity() {

    val pokemonList = ArrayList<PokemonListItemModel>()
    var previousUrl = "null"
    var nextUrl = "null"
    lateinit var pokemonListAdapter: PokemonListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_list)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu_black)
        }

        progressBar.indeterminateDrawable.setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY)

        navView.setNavigationItemSelectedListener { item: MenuItem ->
            //handle menu item clicks
            val id = item.itemId
            when(id){
                R.id.navViewPokemon -> {
                    //Already here
                }
                R.id.navViewGeneration -> {
                    val genIntent = Intent(this, PokemonGenerationsActivity::class.java)
                    startActivity(genIntent)
                }
            }
            true
        }

        pokemonNextBtn.setOnClickListener {
            pokemonListRequest(nextUrl)
        }

        pokemonPreviousBtn.setOnClickListener {
            pokemonListRequest(previousUrl)
        }

        pokemonListAdapter = PokemonListAdapter(this, pokemonList){pokemonListItemModel, imageView ->
            val pokemonDetailIntent = Intent(this, PokemonDetailActivity::class.java)
            pokemonDetailIntent.putExtra(POKEMON_URL_TAG, pokemonListItemModel.url)
            val options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(this, imageView, "pokemonImage")
            startActivity(pokemonDetailIntent, options.toBundle())
        }
        pokemonRecyclerView.adapter = pokemonListAdapter
        pokemonRecyclerView.layoutManager = GridLayoutManager(this, 3)

        pokemonListRequest("$BASE_URL$URL_POKEMON")
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun pokemonListReceived() {
        pokemonListAdapter.notifyDataSetChanged()

        if(previousUrl == "null"){
            pokemonPreviousBtn.visibility = View.GONE
        }else {
            pokemonPreviousBtn.visibility = View.VISIBLE
        }

        if(nextUrl == "null"){
            pokemonNextBtn.visibility = View.GONE
        }else {
            pokemonNextBtn.visibility = View.VISIBLE
        }
    }

    fun pokemonListRequest(url: String){

        progressBar.visibility = View.VISIBLE

        val pokemonListRequest = object : JsonObjectRequest(Method.GET, url, null, Response.Listener { response ->
            //Request success
            pokemonList.clear()
            progressBar.visibility = View.GONE
            try {
                previousUrl = response.getString("previous")
                nextUrl = response.getString("next")

                val resultJsonArray = response.getJSONArray("results")
                for (i in 0 until resultJsonArray.length()){
                    val jsonObject = resultJsonArray.getJSONObject(i)

                    val pokemonName = jsonObject.getString("name")
                    val pokemonUrl = jsonObject.getString("url")

                    pokemonList.add(PokemonListItemModel(pokemonName, pokemonUrl))
                }
                pokemonListReceived()
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
        }){
        }

        App.requestQueue.add(pokemonListRequest)
    }

}
