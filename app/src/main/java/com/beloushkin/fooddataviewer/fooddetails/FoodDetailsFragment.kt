package com.beloushkin.fooddataviewer.fooddetails

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.beloushkin.fooddataviewer.getViewModel
import io.reactivex.Observable
import io.reactivex.disposables.Disposable


class FoodDetailsFragment: Fragment() {

    private val args: FoodDetailsFragmentArgs by navArgs()

    private lateinit var disposable: Disposable

    override fun onStart() {
        super.onStart()
        disposable = Observable.empty<FoodDetailsEvent>()
            .compose(getViewModel(FoodDetailsViewModel::class))
            .subscribe()
    }

    override fun onDestroyView() {
        if (::disposable.isInitialized) {
            disposable.dispose()
        }
        super.onDestroyView()
    }
}