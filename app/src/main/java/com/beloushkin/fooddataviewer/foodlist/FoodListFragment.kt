package com.beloushkin.fooddataviewer.foodlist

import androidx.fragment.app.Fragment
import com.beloushkin.fooddataviewer.R
import com.beloushkin.fooddataviewer.getViewModel
import com.jakewharton.rxbinding3.view.clicks
import kotlinx.android.synthetic.main.food_list_fragment.*

class FoodListFragment: Fragment(R.layout.food_list_fragment) {

    override fun onStart() {
        super.onStart()
        addButton.clicks().map { AddButtonClicked }
            .compose(getViewModel(FoodListViewModel::class))
            .subscribe()

        getViewModel(FoodListViewModel::class)
    }
}