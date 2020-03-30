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
        else -> next(model.copy(activity = false))// TODO
    }
}


class FoodDetailsViewModel @Inject constructor(
    productRepository: ProductRepository
): MobiusVM<FoodDetailsModel, FoodDetailsEvent, FoodDetailsEffect> (
    "FoodDetailsViewModel",
    Update(::foodDetailsUpdate),
    FoodDetailsModel(),
    RxMobius.subtypeEffectHandler<FoodDetailsEffect, FoodDetailsEvent>()
        .build()
)

