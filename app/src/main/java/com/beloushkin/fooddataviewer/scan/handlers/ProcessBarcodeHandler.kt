package com.beloushkin.fooddataviewer.scan.handlers

import android.util.Log
import com.beloushkin.fooddataviewer.model.ProductRepository
import com.beloushkin.fooddataviewer.scan.BarcodeError
import com.beloushkin.fooddataviewer.scan.ProcessBarcode
import com.beloushkin.fooddataviewer.scan.ProductLoaded
import com.beloushkin.fooddataviewer.scan.ScanEvent
import com.beloushkin.fooddataviewer.utils.IdlingResource
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProcessBarcodeHandler @Inject constructor(private val productRepository: ProductRepository,
                                                private val idlingResource: IdlingResource)
    : ObservableTransformer<ProcessBarcode, ScanEvent> {

    override fun apply(upstream: Observable<ProcessBarcode>): ObservableSource<ScanEvent> {
        return upstream.switchMap { effect ->
            productRepository.getProductFromApi(effect.barcode)
                .map {product ->
                    //TODO remove - for testing only!!!
                    idlingResource.decrement()
                    ProductLoaded(product) as ScanEvent
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { error ->
                    //TODO remove - for testing only!!!
                    idlingResource.decrement()
                    Log.e("ProcessBarcode", error.message, error)
                }
                .onErrorReturnItem(BarcodeError)
                .toObservable()
        }
    }
}