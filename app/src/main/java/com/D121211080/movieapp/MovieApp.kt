package com.D121211080.movieapp

import android.app.Application
import com.D121211080.movieapp.data.AppContainer
import com.D121211080.movieapp.data.DefaultAppContainer

class MovieApp : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}