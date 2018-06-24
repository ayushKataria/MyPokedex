package com.example.akat2.mypokedex

import android.app.Application
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

/**
 * Created by Ayush Kataria on 21-06-2018.
 */
class App: Application() {

    companion object {
        lateinit var requestQueue: RequestQueue
    }

    override fun onCreate() {
        super.onCreate()
        requestQueue = Volley.newRequestQueue(this)
    }
}