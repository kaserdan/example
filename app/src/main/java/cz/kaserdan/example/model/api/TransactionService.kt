package cz.kaserdan.example.model.api

import cz.kaserdan.example.model.entity.TransactionInfo
import cz.kaserdan.example.model.entity.TransactionItem
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface TransactionService {

    @GET("transactions")
    fun listAlltransactions(): Observable<List<TransactionItem>>

    @GET("transactions/{transactionId}")
    fun transactionInfo(@Path("transactionId") transactionId: Int): Observable<TransactionInfo>

}