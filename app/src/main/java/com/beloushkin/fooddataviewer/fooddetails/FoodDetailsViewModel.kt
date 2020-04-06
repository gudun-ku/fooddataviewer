package com.beloushkin.fooddataviewer.fooddetails

import com.beloushkin.fooddataviewer.MobiusVM
import com.beloushkin.fooddataviewer.model.ProductRepository
import com.spotify.mobius.Next
import com.spotify.mobius.Next.next
import com.spotify.mobius.Update
import com.spotify.mobius.rx2.RxMobius
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
        is ActionButtonClicked -> TODO()
    }
}


class FoodDetailsViewModel @Inject constructor(
    productRepository: ProductRepository
): MobiusVM<FoodDetailsModel, FoodDetailsEvent, FoodDetailsEffect> (
    "FoodDetailsViewModel",
    Update(::foodDetailsUpdate),
    FoodDetailsModel(),
    RxMobius.subtypeEffectHandler<FoodDetailsEffect, FoodDetailsEvent>()
            // it comes next
//        .addTransformer(LoadProduct::class.java) { upstream ->
//            upstream.switchMap { effect ->
//                productRepository.getProductFromApi(effect.barcode)
//            }
//        }
        .build()
)

