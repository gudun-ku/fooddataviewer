package com.beloushkin.fooddataviewer.foodlist

import com.beloushkin.fooddataviewer.MobiusVM
import com.beloushkin.fooddataviewer.model.ProductRepository
import com.beloushkin.fooddataviewer.utils.Navigator
import com.spotify.mobius.Next
import com.spotify.mobius.Next.dispatch
import com.spotify.mobius.Next.next
import com.spotify.mobius.Update
import com.spotify.mobius.rx2.RxMobius
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

fun foodListUpdate(
    model: FoodListModel,
    event: FoodListEvent
): Next<FoodListModel, FoodListEffect> {
    return when(event) {
        is Initial -> dispatch(setOf(ObserveProducts))
        is AddButtonClicked -> dispatch(setOf(NavigateToScanner))
        is ProductsLoaded -> next(model.copy(products = event.products))
        is ProductClicked -> dispatch(setOf(NavigateToFoodDetails(event.barcode)))
    }
}

class FoodListViewModel @Inject constructor(
    productRepository: ProductRepository,
    navigator: Navigator
): MobiusVM<FoodListModel, FoodListEvent, FoodListEffect>(
    "FoodListViewModel",
    Update(::foodListUpdate),
    FoodListModel(),
    RxMobius.subtypeEffectHandler<FoodListEffect, FoodListEvent>()
        .addTransformer(ObserveProducts::class.java) { upstream ->
            upstream.switchMap {
                productRepository.get()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map { products -> ProductsLoaded(products) }
            }
        }
        .addAction(NavigateToScanner::class.java) {
            navigator.to(FoodListFragmentDirections.scan())
        }
        .addConsumer(NavigateToFoodDetails::class.java) {effect ->
            navigator.to(FoodListFragmentDirections.foodDetails(effect.barcode))
        }
        .build()
)

