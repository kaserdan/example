package cz.kaserdan.example.di

import cz.kaserdan.example.ui.list.ListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * Created by kaserdan on 9.12.17.
 */
@Module
abstract class FragmentBuildersModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeListFragment(): ListFragment

}