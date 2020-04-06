package com.beloushkin.fooddataviewer.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.beloushkin.fooddataviewer.model.dto.ProductDto
import io.reactivex.Completable
import io.reactivex.Single

@Dao
abstract class ProductDao {

    @Query("SELECT * FROM ProductDto WHERE id = :barcode")
    abstract fun getProduct(barcode: String): Single<ProductDto>

    @Insert
    abstract fun insert(productDto: ProductDto): Completable
}