package com.beloushkin.fooddataviewer.di

import android.content.Context
import com.beloushkin.fooddataviewer.model.database.ProductDao
import com.beloushkin.fooddataviewer.scan.TestFrameProcessorOnSubscribe
import com.beloushkin.fooddataviewer.scan.utils.FrameProcessorOnSubscribe
import com.beloushkin.fooddataviewer.utils.IdlingResource
import com.beloushkin.fooddataviewer.utils.TestIdlingResource
import dagger.*
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class, ApiModule::class, DatabaseModule::class, TestModule::class])
interface TestComponent : ApplicationComponent {

    fun idlingResource(): IdlingResource

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        @BindsInstance
        fun productDao(productDao: ProductDao): Builder

        fun build(): TestComponent
    }
}

@Module
object TestModule {

    @Singleton
    @JvmStatic
    @Provides
    fun frameProcessorOnSubscribe(): FrameProcessorOnSubscribe = TestFrameProcessorOnSubscribe()

    @Provides
    @Singleton
    @JvmStatic
    fun idlingResource(): IdlingResource = TestIdlingResource()
}