package cz.kaserdan.example.model.repository

import cz.kaserdan.example.model.entity.TransactionInfo
import cz.kaserdan.example.model.entity.TransactionItem
import io.reactivex.Observable

interface TransactionRepository {

    fun fetchTransactions(filter: TransactionItem.TransactionFilter?): Observable<List<TransactionItem>>

    fun fetchTransactionInfo(transactionId: Int): Observable<TransactionInfo>

}