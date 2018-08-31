package cz.kaserdan.example.ui.list

import cz.kaserdan.example.model.entity.TransactionItem
import cz.kaserdan.example.model.repository.TransactionRepository
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class ListActionProcessorHolder @Inject constructor(private val repository: TransactionRepository) {


    val loadTransactionsProcessor: ObservableTransformer<TransactionItem.TransactionFilter, ListActionResult> =
        ObservableTransformer<TransactionItem.TransactionFilter, ListActionResult> { actions ->
            actions.flatMap {
                repository.fetchTransactions()
                    .map { alarms -> AlarmListResult.LoadAlarms.Success(alarms) }
                    .cast(AlarmListResult.LoadAlarms::class.java)
                    .onErrorReturn(AlarmListResult.LoadAlarms::Failure)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .startWith()
            }

}