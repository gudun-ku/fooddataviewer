package com.beloushkin.fooddataviewer


import com.beloushkin.fooddataviewer.model.database.ProductDao
import com.beloushkin.fooddataviewer.model.dto.ProductDto
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import com.beloushkin.fooddataviewer.di.DaggerTestComponent
import java.io.IOException

class AndroidTestApplication : App() {


    val productDtosSubject = PublishSubject.create<List<ProductDto>>()
    val productDtoSubject = PublishSubject.create<ProductDto>()
    private val productDao: ProductDao = object : ProductDao() {

        override fun get(): Observable<List<ProductDto>> {
            return productDtosSubject
        }

        override fun getProduct(barcode: String): Single<ProductDto> {
            //throw NotImplementedError("Not implemented in instrumented testing") as Throwable
            return Single.error(NotImplementedError("Not implemented in instrumented testing"))
        }

        override fun insert(productDto: ProductDto): Completable {
            throw NotImplementedError("Not implemented in instrumented testing")
        }

        override fun delete(barcode: String): Completable {
            throw NotImplementedError("Not implemented in instrumented testing")
        }

    }

    override val component by lazy {
        DaggerTestComponent.builder()
            .context(this)
            .productDao(productDao)
            .build()
    }
}