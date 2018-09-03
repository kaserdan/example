package cz.kaserdan.example.di

import android.app.Application
import cz.kaserdan.example.ExampleApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent : AndroidInjector<ExampleApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}

