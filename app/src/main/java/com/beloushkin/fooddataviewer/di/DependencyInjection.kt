package com.beloushkin.fooddataviewer.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.beloushkin.fooddataviewer.R
import com.beloushkin.fooddataviewer.foodlist.FoodListViewModel
import com.beloushkin.fooddataviewer.scan.ScanViewModel
import com.beloushkin.fooddataviewer.utils.ActivityService
import com.beloushkin.fooddataviewer.utils.Navigator
import dagger.*
import dagger.multibindings.IntoMap
import kotlinx.android.synthetic.main.activity_root.*
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun viewModelFactory(): ViewModelProvider.Factory

    fun activityService(): ActivityService
}

@Module
object ApplicationModule {
    @Provides
    @Singleton
    @JvmStatic
    fun viewModels(providers: MutableMap<Class<out ViewModel>, Provider<ViewModel>>)
            : ViewModelProvider.Factory {
        return ViewModelFactory(providers)
    }

    @Provides
    @Singleton
    @JvmStatic
    fun activityService(): ActivityService = ActivityService()

    @Provides
    @Singleton
    @JvmStatic
    fun navigator(activityService: ActivityService): Navigator {
        return Navigator(R.id.navigationHostFragment, activityService)
    }

}

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(FoodListViewModel::class)
    abstract fun foodListViewModel(viewModel: FoodListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ScanViewModel::class)
    abstract fun scanViewModel(viewModel: ScanViewModel): ViewModel
}