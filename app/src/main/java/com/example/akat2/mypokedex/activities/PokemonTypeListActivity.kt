package com.example.akat2.mypokedex.activities

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.akat2.mypokedex.App
import com.example.akat2.mypokedex.R
import com.example.akat2.mypokedex.adapters.PokemonTypeGridAdapter
import com.example.akat2.mypokedex.models.TypesListItemModel
import com.example.akat2.mypokedex.utils.BASE_URL
import com.example.akat2.mypokedex.utils.TYPE_NAME_TAG
import com.example.akat2.mypokedex.utils.TYPE_URL_TAG
import com.example.akat2.mypokedex.utils.URL_TYPE
import kotlinx.android.synthetic.main.activity_pokemon_type_list.*
import org.json.JSONException

class PokemonTypeListActivity : AppCompatActivity() {

    val typeList = ArrayList<TypesListItemModel>()
    private lateinit var typeListAdapter: PokemonTypeGridAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_type_list)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu_white)
        }

        typeProgressBar.indeterminateDrawable.setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY)
        typeProgressBar.visibility = View.VISIBLE

        typeNavView.setNavigationItemSelectedListener { item: MenuItem ->
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
                    //Already here
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

        loadTypeList()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home ->{
                pokemonTypeDrawerLayout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun typeListLoaded() {
        typeListAdapter = PokemonTypeGridAdapter(this, typeList) { typesListItemModel ->
            val typeDetailIntent = Intent(this, PokemonTypeDetailActivity::class.java)
            typeDetailIntent.putExtra(TYPE_NAME_TAG, typesListItemModel.name)
            typeDetailIntent.putExtra(TYPE_URL_TAG, typesListItemModel.url)
            startActivity(typeDetailIntent)

        }
        pokemonTypeTypeList.adapter = typeListAdapter
        pokemonTypeTypeList.layoutManager = GridLayoutManager(this, 3)
    }

    private fun loadTypeList() {

        val url = "$BASE_URL$URL_TYPE"

        val typeListRequest = object : JsonObjectRequest(Method.GET, url, null, Response.Listener { response ->
            typeProgressBar.visibility = View.GONE
            try{

                val typeArrayJsonObject = response.getJSONArray("results")

                for(i in 0 until typeArrayJsonObject.length()){
                    val typeJsonObject = typeArrayJsonObject.getJSONObject(i)
                    val name = typeJsonObject.getString("name")
                    val url = typeJsonObject.getString("url")
                    typeList.add(TypesListItemModel(name, url))
                }
                typeListLoaded()
            }catch (e: JSONException){
                Toast.makeText(this, e.localizedMessage, Toast.LENGTH_LONG).show()
                Log.d("EXC", e.localizedMessage)
            }
        }, Response.ErrorListener { error ->
            //Request failed
            typeProgressBar.visibility = View.GONE
            Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
            error.printStackTrace()
        }) {}

        App.requestQueue.add(typeListRequest)
    }
}
