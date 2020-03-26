package com.beloushkin.fooddataviewer

import android.app.Application
import com.beloushkin.fooddataviewer.di.DaggerApplicationComponent

class App: Application() {

    val component by lazy {
        DaggerApplicationComponent.builder()
            .build()
    }
}