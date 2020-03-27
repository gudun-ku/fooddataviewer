package com.beloushkin.fooddataviewer.utils

import androidx.navigation.NavDirections
import androidx.navigation.findNavController

class Navigator(private val viewId: Int, private val activityService: ActivityService) {
    private val navController
        get() =  activityService.activity.findNavController(viewId)

    fun to(directions: NavDirections){
        navController.navigate(directions)
    }
}