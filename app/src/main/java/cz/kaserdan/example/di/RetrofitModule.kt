package cz.kaserdan.example.di

import cz.kaserdan.example.model.api.TransactionService
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

class RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder().baseUrl("http://demo0569565.mockable.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideTransactionService(retrofit: Retrofit): TransactionService = retrofit.create(TransactionService::class.java)

}