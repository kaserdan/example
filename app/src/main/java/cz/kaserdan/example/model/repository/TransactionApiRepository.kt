package cz.kaserdan.example.model.repository

import cz.kaserdan.example.model.api.TransactionService
import cz.kaserdan.example.model.entity.TransactionInfo
import cz.kaserdan.example.model.entity.TransactionItem
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class TransactionApiRepository @Inject constructor(private val transactionService: TransactionService) : TransactionRepository {

    private var listDisposable: Disposable? = null

    override fun fetchTransactions(filter: TransactionItem.TransactionFilter?): Observable<List<TransactionItem>> {
        listDisposable?.takeIf { !it.isDisposed }?.dispose()
        return transactionService.listAlltransactions()
            .map { list -> filter?.let { filter -> list.filter { it.direction == filter.direction } } ?: list }
            .doOnSubscribe { listDisposable = it }
    }

    override fun fetchTransactionInfo(transactionId: Int): Observable<TransactionInfo> = transactionService.transactionInfo(transactionId)

}