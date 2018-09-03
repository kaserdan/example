package cz.kaserdan.example.di

import cz.kaserdan.example.ui.detail.DetailFragment
import cz.kaserdan.example.ui.list.ListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeListFragment(): ListFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailFragment(): DetailFragment

}