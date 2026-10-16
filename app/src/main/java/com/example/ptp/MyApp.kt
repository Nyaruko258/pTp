package com.example.ptp

import android.app.Application
import android.util.Log
import com.example.ptp.`object`.P2pContainer

class MyApp : Application() {
    val p2pContainer : P2pContainer by lazy { P2pContainer() }

    companion object{
        lateinit var instance : MyApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("CatCat","Init App")
        instance = this
    }
}