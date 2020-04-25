package com.beloushkin.fooddataviewer.fooddetails

import com.beloushkin.fooddataviewer.MobiusVM
import com.beloushkin.fooddataviewer.model.ProductRepository
import com.beloushkin.fooddataviewer.utils.IdlingResource
import com.spotify.mobius.Next
import com.spotify.mobius.Next.*
import com.spotify.mobius.Update
import com.spotify.mobius.rx2.RxMobius
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

fun foodDetailsUpdate(
    model: FoodDetailsModel,
    event: FoodDetailsEvent
): Next<FoodDetailsModel, FoodDetailsEffect>{
    return when(event){
        is Initial -> next(
            model.copy(activity = true),
            setOf(LoadProduct(event.barcode))
        )
        is ProductLoaded -> next(model.copy(activity = false, product = event.product))
        is ErrorLoadingProduct -> next(model.copy(activity = false, error = true))
        is ActionButtonClicked -> if (model.product != null) {
            if (model.product.saved) {
                next<FoodDetailsModel, FoodDetailsEffect>(
                    model.copy(product = model.product.copy(saved = !model.product.saved)),
                    setOf(DeleteProduct(model.product.id))
                )
            } else {
                next<FoodDetailsModel, FoodDetailsEffect>(
                    model.copy(product = model.product.copy(saved = !model.product.saved)),
                    setOf(SaveProduct(model.product))
                )
            }
        } else {
            noChange()
        }
    }
}


class FoodDetailsViewModel @Inject constructor(
    productRepository: ProductRepository,
    //TODO - for testing purposes, can be removed
    idlingResource: IdlingResource
): MobiusVM<FoodDetailsModel, FoodDetailsEvent, FoodDetailsEffect> (
    "FoodDetailsViewModel",
    Update(::foodDetailsUpdate),
    FoodDetailsModel(),
    RxMobius.subtypeEffectHandler<FoodDetailsEffect, FoodDetailsEvent>()

        .addTransformer(LoadProduct::class.java) { upstream ->
            upstream.switchMap { effect ->
                productRepository.loadProduct(barcode = effect.barcode)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .toObservable()
                    .map {product ->
                        ProductLoaded(product) as FoodDetailsEvent
                    }
                    .onErrorReturn {
                        ErrorLoadingProduct
                    }
                    .doFinally {
                        //TODO - for testing purposes, can be removed
                        //idlingResource.decrement()
                    }
            }
        }
        .addTransformer(SaveProduct::class.java) { upstream ->
            upstream.switchMap { effect ->
                productRepository.saveProduct(effect.product)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .toObservable<FoodDetailsEvent>()
            }
        }
        .addTransformer(DeleteProduct::class.java) { upstream ->
            upstream.switchMap { effect ->
                productRepository.deleteProduct(effect.barcode)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .toObservable<FoodDetailsEvent>()
            }
        }
        .build()
)

