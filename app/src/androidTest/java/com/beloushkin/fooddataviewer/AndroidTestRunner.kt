package com.beloushkin.fooddataviewer

import android.app.Application
import android.app.Instrumentation
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.github.tmurakami.dexopener.DexOpener

@Suppress("unused")
class AndroidTestRunner: AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        DexOpener.install(this) // Call me first!
        return Instrumentation.newApplication(AndroidTestApplication::class.java, context)
    }
}