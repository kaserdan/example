package cz.kaserdan.example.di

import android.arch.lifecycle.ViewModel
import cz.kaserdan.example.ui.list.ListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel::class)
    internal abstract fun bindListViewModel(productListViewModel: ListViewModel): ViewModel

}