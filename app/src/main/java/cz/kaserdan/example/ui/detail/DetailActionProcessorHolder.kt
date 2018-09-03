package cz.kaserdan.example.ui.detail

import cz.kaserdan.example.model.entity.TransactionItem
import cz.kaserdan.example.model.repository.TransactionRepository
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailActionProcessorHolder @Inject constructor(private val repository: TransactionRepository) {

    val loadTransactionsProcessor: ObservableTransformer<TransactionItem, DetailActionResult> =
            ObservableTransformer { actions ->
                actions.flatMap { item ->
                    repository.fetchTransactionInfo(item.id)
                            .map { transactions -> DetailActionResult.LoadItems.Success(transactions) }
                            .cast(DetailActionResult.LoadItems::class.java)
                            .onErrorReturn {
                                it.printStackTrace()
                                DetailActionResult.LoadItems.Error
                            }
                            .startWith(DetailActionResult.LoadItems.Loading(item))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread(), true)
                }

            }

}