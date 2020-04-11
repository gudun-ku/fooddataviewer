package com.beloushkin.fooddataviewer.foodlist

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.beloushkin.fooddataviewer.R
import com.beloushkin.fooddataviewer.foodlist.widget.FoodListAdapter
import com.beloushkin.fooddataviewer.getViewModel
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.food_list_fragment.*

class FoodListFragment: Fragment(R.layout.food_list_fragment) {

    lateinit var disposable: Disposable

    override fun onStart() {
        super.onStart()
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = FoodListAdapter()
        recyclerView.adapter = adapter

        disposable = Observable.mergeArray(
            addButton.clicks().map { AddButtonClicked },
            adapter.productClicks.map { product -> ProductClicked(product.id) }
        )
        .compose(getViewModel(FoodListViewModel::class).init(Initial))
        .subscribe { model ->
            adapter.submitList(model.products)
        }
    }

    override fun onDestroy() {
        if(::disposable.isInitialized) disposable.dispose()
        super.onDestroy()
    }
}