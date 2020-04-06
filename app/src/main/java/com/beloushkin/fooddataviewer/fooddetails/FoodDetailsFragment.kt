package com.beloushkin.fooddataviewer.fooddetails

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.beloushkin.fooddataviewer.R
import com.beloushkin.fooddataviewer.getViewModel
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.food_details_fragment.*


class FoodDetailsFragment: Fragment(R.layout.food_details_fragment) {

    private val args: FoodDetailsFragmentArgs by navArgs()

    private lateinit var disposable: Disposable

    override fun onStart() {
        super.onStart()

        // Mobius loop starts 1st time, when fragment starts, so give its init method a barcode
        disposable = actionButton.clicks().map { ActionButtonClicked }
            .compose(getViewModel(FoodDetailsViewModel::class).init(Initial(args.barcode)))
            .subscribe()
    }

    override fun onDestroyView() {
        if (::disposable.isInitialized) {
            disposable.dispose()
        }
        super.onDestroyView()
    }
}