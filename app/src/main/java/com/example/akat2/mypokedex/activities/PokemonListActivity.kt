package com.example.akat2.mypokedex.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
import com.example.akat2.mypokedex.utils.URL_POKEMON
import kotlinx.android.synthetic.main.activity_pokemon_list.*
import org.json.JSONException

class PokemonListActivity : AppCompatActivity() {

    val context = this
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

        navView.setNavigationItemSelectedListener { item: MenuItem ->
            //handle menu item clicks
            true
        }

        pokemonNextBtn.setOnClickListener {
            pokemonListRequest(nextUrl)
        }

        pokemonPreviousBtn.setOnClickListener {
            pokemonListRequest(previousUrl)
        }

        pokemonListAdapter = PokemonListAdapter(this, pokemonList)
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

        val pokemonListRequest = object : JsonObjectRequest(Method.GET, url, null, Response.Listener { response ->
            //Request success
            pokemonList.clear()
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
                Toast.makeText(context, e.localizedMessage, Toast.LENGTH_LONG).show()
                Log.d("EXC", e.localizedMessage)
            }
        }, Response.ErrorListener { error ->
            //Request failed
            Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
            error.printStackTrace()
        }){
        }

        App.requestQueue.add(pokemonListRequest)
    }
}
