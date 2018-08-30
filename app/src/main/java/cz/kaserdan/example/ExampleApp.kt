package cz.kaserdan.example


import cz.kaserdan.example.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.DaggerApplication

class ExampleApp : DaggerApplication(), HasActivityInjector {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        AppInjector.init(this)
        return AppInjector.component
    }

}