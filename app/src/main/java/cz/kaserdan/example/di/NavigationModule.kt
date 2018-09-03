package cz.kaserdan.example.di

import cz.kaserdan.example.navigation.AppNavigator
import cz.kaserdan.example.navigation.NavigationCommander
import cz.kaserdan.example.navigation.NavigationRouter
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Suppress("unused")
@Module
abstract class NavigationModule {

    @Binds
    @Singleton
    internal abstract fun bindNavigationCommander(navigator: AppNavigator): NavigationCommander

    @Binds
    @Singleton
    internal abstract fun bindNavigationRouter(navigator: AppNavigator): NavigationRouter

}