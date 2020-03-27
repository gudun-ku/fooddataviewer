package com.beloushkin.fooddataviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.beloushkin.fooddataviewer.utils.ActivityService

class RootActivity : AppCompatActivity() {

    private lateinit var activityService: ActivityService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        activityService = applicationContext.component.activityService()
        activityService.onCreate(this)
    }

    override fun onDestroy() {
        activityService.onDestroy(this)
        super.onDestroy()
    }
}
