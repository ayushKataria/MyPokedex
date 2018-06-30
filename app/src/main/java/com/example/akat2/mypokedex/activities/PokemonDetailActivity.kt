package com.example.akat2.mypokedex.activities

import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.akat2.mypokedex.App
import com.example.akat2.mypokedex.R
import com.example.akat2.mypokedex.models.Ability
import com.example.akat2.mypokedex.models.Pokemon
import com.example.akat2.mypokedex.models.PokemonStat
import com.example.akat2.mypokedex.utils.*
import kotlinx.android.synthetic.main.activity_pokemon_detail.*
import org.json.JSONException

class PokemonDetailActivity : AppCompatActivity() {

    lateinit var pokemonUrl: String

    companion object {
        var pokemonDetail = Pokemon(0, "", 0, 0, 0, ArrayList(), ArrayList(), HashMap(),
                ArrayList(), HashMap(), ArrayList(), HashMap(), ArrayList(),
                HashMap())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detail)

        pokemonUrl = intent.getStringExtra(POKEMON_URL_TAG)

        Glide.with(this)
                .load(Utils.getImageUrl(pokemonUrl))
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }

                })
                .into(pokemonDetailImageView)

        pokemonDetailRequest()
    }

    fun updateUi() {
        pokemonDetailNameTxt.text = pokemonDetail.name
        pokemonDetailHeightTxt.text = "${((pokemonDetail.height.toDouble())/10).toString()}m"
        pokemonDetailWeightTxt.text = "${((pokemonDetail.weight.toDouble())/10).toString()}kg"

    }


    fun pokemonDetailRequest() {

        val pokemonDetailRequest = object : JsonObjectRequest(Method.GET, pokemonUrl, null, Response.Listener { response ->

            try {

                pokemonDetail.id = response.getInt("id")
                pokemonDetail.name = response.getString("name")
                pokemonDetail.baseExperience = response.getInt("base_experience")
                pokemonDetail.height = response.getInt("height")
                pokemonDetail.weight = response.getInt("weight")
                val abilitiesJsonArray = response.getJSONArray("abilities")

                for (i in 0 until abilitiesJsonArray.length()){
                    val abilityJsonObject = abilitiesJsonArray.getJSONObject(i)
                    val isHidden = abilityJsonObject.getBoolean("is_hidden")
                    val abilityName = abilityJsonObject.getJSONObject("ability").getString("name")
                    val abilityUrl = abilityJsonObject.getJSONObject("ability").getString("url")
                    val slot = abilityJsonObject.getInt("slot")
                    val ability = Ability(isHidden, abilityName, slot, abilityUrl)
                    pokemonDetail.abilities.add(ability)
                }

                val gameVersionJsonArray = response.getJSONArray("game_indices")

                for (i in 0 until gameVersionJsonArray.length()){
                    val gameVersionJsonObject = gameVersionJsonArray.getJSONObject(i)
                    pokemonDetail.gameVersionNames.add(gameVersionJsonObject.getJSONObject("version").getString("name"))
                    pokemonDetail.gameVersionUrls.put(gameVersionJsonObject.getJSONObject("version").getString("name"),
                            gameVersionJsonObject.getJSONObject("version").getString("url"))
                }

                val typeJsonArray = response.getJSONArray("types")

                for (i in 0 until typeJsonArray.length()) {
                    val typeJsonObject = typeJsonArray.getJSONObject(i)
                    pokemonDetail.types.add(typeJsonObject.getJSONObject("type").getString("name"))
                    pokemonDetail.typeUrls.put(typeJsonObject.getJSONObject("type").getString("name"),
                            typeJsonObject.getJSONObject("type").getString("url"))
                }

                val statJsonArray = response.getJSONArray("stats")

                for (i in 0 until statJsonArray.length()) {
                    val statJsonObject = statJsonArray.getJSONObject(i)
                    val baseStat = statJsonObject.getInt("base_stat")
                    val effort = statJsonObject.getInt("effort")
                    val name = statJsonObject.getJSONObject("stat").getString("name")
                    val url = statJsonObject.getJSONObject("stat").getString("url")

                    pokemonDetail.stats.add(PokemonStat(name, baseStat, effort,  url))
                }

                val spritesJsonObject = response.getJSONObject("sprites")
                pokemonDetail.sprites.put(BACK_FEMALE, spritesJsonObject.getString(BACK_FEMALE))
                pokemonDetail.sprites.put(BACK_SHINY_FEMALE, spritesJsonObject.getString(BACK_SHINY_FEMALE))
                pokemonDetail.sprites.put(BACK_DEFAULT, spritesJsonObject.getString(BACK_DEFAULT))
                pokemonDetail.sprites.put(FRONT_FEMALE, spritesJsonObject.getString(FRONT_FEMALE))
                pokemonDetail.sprites.put(FRONT_SHINY_FEMALE, spritesJsonObject.getString(FRONT_SHINY_FEMALE))
                pokemonDetail.sprites.put(BACK_SHINY, spritesJsonObject.getString(BACK_SHINY))
                pokemonDetail.sprites.put(FRONT_DEFAULT, spritesJsonObject.getString(FRONT_DEFAULT))
                pokemonDetail.sprites.put(FRONT_SHINY, spritesJsonObject.getString(FRONT_SHINY))

                val movesJsonArray = response.getJSONArray("moves")

                for (i in 0 until movesJsonArray.length()) {
                    val moveJsonObject = movesJsonArray.getJSONObject(i).getJSONObject("move")
                    pokemonDetail.moves.add(moveJsonObject.getString("name"))
                    pokemonDetail.moveUrls.put(moveJsonObject.getString("name"), moveJsonObject.getString("url"))
                }
                updateUi()

            }catch (e: JSONException){
                Toast.makeText(this, e.localizedMessage, Toast.LENGTH_LONG).show()
                Log.d("EXC", e.localizedMessage)
            }

        }, Response.ErrorListener { error ->
            Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
            error.printStackTrace()
        }){}

        App.requestQueue.add(pokemonDetailRequest)

    }
}
