package cz.kaserdan.example.model.repository

import cz.kaserdan.example.model.api.TransactionService
import cz.kaserdan.example.model.entity.TransactionInfo
import cz.kaserdan.example.model.entity.TransactionItem
import io.reactivex.Observable
import javax.inject.Inject

class TransactionApiRepository @Inject constructor(private val transactionService: TransactionService) : TransactionRepository {

    override fun fetchTransactions(): Observable<List<TransactionItem>> = transactionService.listAlltransactions()

    override fun fetchTransactionInfo(transactionId: Int): Observable<TransactionInfo> = transactionService.transactionInfo(transactionId)

}