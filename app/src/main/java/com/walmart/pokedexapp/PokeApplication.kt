package com.walmart.pokedexapp

import android.app.Application
import com.walmart.pokedexapp.module.networkModule
import com.walmart.pokedexapp.module.repositoryModule
import com.walmart.pokedexapp.module.viewModelModule
import org.koin.core.context.startKoin

class PokeApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(networkModule, repositoryModule, viewModelModule)
        }
    }
}