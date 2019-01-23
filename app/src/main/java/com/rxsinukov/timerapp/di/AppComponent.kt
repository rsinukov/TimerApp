package com.rxsinukov.timerapp.di

import android.content.Context
import com.rxsinukov.common.qualifiers.ApplicationContext
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        FeaturesModule::class,
        UtilsModule::class
    ]
)
interface AppComponent : MyComponent {

    fun inject(componentsHolder: ComponentsHolder)

    @Component.Builder
    interface Builder : ComponentBuilder<AppComponent> {

        @BindsInstance
        fun application(@ApplicationContext application: Context): Builder

        override fun build(): AppComponent
    }

}
