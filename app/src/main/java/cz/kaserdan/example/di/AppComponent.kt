package cz.kaserdan.example.di

import android.app.Application
import cz.kaserdan.timetostop.TimeToStopApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector


/**
 * Created by kaserdan on 23.10.17.
 */
@ApplicationScope
@Component(modules = [AppModule::class])
interface AppComponent : AndroidInjector<TimeToStopApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}

