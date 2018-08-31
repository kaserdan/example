package cz.kaserdan.example.di

import android.app.Application
import android.content.Context
import android.support.v4.app.Fragment
import cz.kaserdan.example.ui.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.Multibinds


@Module(
        includes = [AndroidInjectionModule::class, ViewModelModule::class, RepositoryModule::class, RetrofitModule::class]
)
abstract class AppModule {

    @Multibinds
    abstract fun bindNativeFragments(): Map<Class<out Fragment>, AndroidInjector.Factory<out Fragment>>

    @ContributesAndroidInjector(modules = [(FragmentBuildersModule::class)])
    abstract fun contributeMainActivity(): MainActivity

    @Binds
    abstract fun application(app: Application): Context

}