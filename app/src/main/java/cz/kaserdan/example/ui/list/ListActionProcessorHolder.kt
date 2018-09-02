package cz.kaserdan.example.ui.list

import cz.kaserdan.example.model.entity.TransactionItem
import cz.kaserdan.example.model.repository.TransactionRepository
import cz.kaserdan.example.navigation.NavigationRouter
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListActionProcessorHolder @Inject constructor(
    private val repository: TransactionRepository,
    private val navigator: NavigationRouter
) {


    val loadTransactionsProcessor: ObservableTransformer<TransactionItem.TransactionFilter, ListActionResult> =
        ObservableTransformer { actions ->
            actions.flatMap { filter ->
                repository.fetchTransactions(filter)
                    .map { transactions -> ListActionResult.LoadItems.Success(transactions) }
                    .cast(ListActionResult.LoadItems::class.java)
                    .onErrorReturnItem(ListActionResult.LoadItems.Error)
                    .startWith(ListActionResult.LoadItems.Loading)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread(), true)
            }

        }

    val showTransactionInfoProcessor: ObservableTransformer<TransactionItem, ListActionResult> =
        ObservableTransformer {
            it.flatMap { transaction -> navigator.showTransactionDetail(transaction).toObservable<ListActionResult>() }
        }

}