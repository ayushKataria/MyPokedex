package com.example.akat2.mypokedex.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.akat2.mypokedex.App
import com.example.akat2.mypokedex.R
import com.example.akat2.mypokedex.adapters.BerryListAdapter
import com.example.akat2.mypokedex.models.PokemonListItemModel
import com.example.akat2.mypokedex.utils.BASE_URL
import com.example.akat2.mypokedex.utils.URL_BERRY
import kotlinx.android.synthetic.main.activity_pokemon_berry_list.*
import org.json.JSONException

class PokemonBerryListActivity : AppCompatActivity() {

    val berryArrayList = ArrayList<PokemonListItemModel>()
    var previousUrl = "null"
    var nextUrl = "null"
    private lateinit var berryAdapter:  BerryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_berry_list)
        setSupportActionBar(berryListToolbar)

        berryListprogressBar.visibility = View.VISIBLE

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu_white)
        }

        berryNavView.setNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.navViewPokemon -> {
                    val pokemonIntent = Intent(this, PokemonListActivity::class.java)
                    pokemonIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(pokemonIntent)
                    finish()
                }
                R.id.navViewGeneration -> {
                    val genIntent = Intent(this, PokemonGenerationsActivity::class.java)
                    genIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(genIntent)
                    finish()
                }
                R.id.navViewType -> {
                    val typeIntent = Intent(this, PokemonTypeListActivity::class.java)
                    typeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(typeIntent)
                    finish()
                }
                R.id.navViewBerry -> {
                    //Already here
                }
            }
            true
        }

        berryNextBtn.setOnClickListener {
            loadBerryList(nextUrl)
        }

        berryPreviousBtn.setOnClickListener {
            loadBerryList(previousUrl)
        }

        berryAdapter = BerryListAdapter(this, berryArrayList) { _, _ ->
            //TODO: Item click
        }

        berryList.adapter = berryAdapter
        berryList.layoutManager = GridLayoutManager(this, 3)

        loadBerryList("$BASE_URL$URL_BERRY")
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                berryDrawerLayout.openDrawer(Gravity.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun berryListReceived() {

        berryAdapter.notifyDataSetChanged()

        if(previousUrl == "null")
            berryPreviousBtn.visibility = View.GONE
        else
            berryPreviousBtn.visibility = View.VISIBLE

        if(nextUrl == "null")
            berryNextBtn.visibility = View.GONE
        else
            berryNextBtn.visibility = View.VISIBLE

    }

    private fun loadBerryList(url: String) {

        val berryListRequest = object : JsonObjectRequest(Method.GET, url, null, Response.Listener { response ->
            berryListprogressBar.visibility = View.GONE
            berryArrayList.clear()
            try {

                previousUrl = response.getString("previous")
                nextUrl = response.getString("next")

                val resultJsonArray = response.getJSONArray("results")
                for(i in 0 until resultJsonArray.length()) {
                    val berryJsonObject = resultJsonArray.getJSONObject(i)

                    val name = berryJsonObject.getString("name")
                    val url = berryJsonObject.getString("url")
                    berryArrayList.add(PokemonListItemModel(name, url))
                }
                berryListReceived()
            }catch (e: JSONException) {
                Toast.makeText(this, e.localizedMessage, Toast.LENGTH_LONG).show()
                Log.d("EXC", e.localizedMessage)
            }

        }, Response.ErrorListener { error ->
            //Request failed
            berryListprogressBar.visibility = View.GONE
            Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
            error.printStackTrace()
        }){}

        App.requestQueue.add(berryListRequest)
    }
}
