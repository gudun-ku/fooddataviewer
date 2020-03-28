package com.beloushkin.fooddataviewer.scan.handlers

import com.beloushkin.fooddataviewer.scan.ProcessBarcode
import com.beloushkin.fooddataviewer.scan.ScanEvent
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProcessBarcodeHandler @Inject constructor()
    : ObservableTransformer<ProcessBarcode, ScanEvent> {

    override fun apply(upstream: Observable<ProcessBarcode>): ObservableSource<ScanEvent> {
        // TODO - add real code to call API
        return upstream.flatMap { effect ->
            Observable.create<ScanEvent> { emitter ->
                    emitter.onComplete()
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        }
    }
}