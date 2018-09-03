package cz.kaserdan.example.di

import cz.kaserdan.example.model.repository.TransactionApiRepository
import cz.kaserdan.example.model.repository.TransactionRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Suppress("unused")
@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    internal abstract fun bindRepository(transactionApiRepository: TransactionApiRepository): TransactionRepository

}