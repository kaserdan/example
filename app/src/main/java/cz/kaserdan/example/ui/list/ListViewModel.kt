package cz.kaserdan.example.ui.list

import com.jakewharton.rxrelay2.PublishRelay
import cz.kaserdan.example.model.entity.TransactionItem
import cz.kaserdan.example.ui.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import javax.inject.Inject

class ListViewModel @Inject constructor(processor: ListActionProcessorHolder) : BaseViewModel<ListViewState>() {

    val loadTransactionsFiltered: Consumer<TransactionItem.TransactionFilter>
    val itemClick: Consumer<TransactionItem>

    override val stateOutput: Observable<ListViewState>

    init {
        val processedInputs = ArrayList<Observable<ListActionResult>>()
        loadTransactionsFiltered = PublishRelay.create<TransactionItem.TransactionFilter>().apply {
            processedInputs.add(this.startWith(TransactionItem.TransactionFilter()).compose(processor.loadTransactionsProcessor))
        }
        itemClick = PublishRelay.create<TransactionItem>().apply {
            processedInputs.add(compose(processor.showTransactionInfoProcessor))
        }
        stateOutput = Observable.merge(processedInputs)
            .scan(ListViewState(), { previousState, result -> result.reduce(previousState) })
            .replay(1)
            .autoConnect(0, { it.toDisposeBag() })
            .observeOn(AndroidSchedulers.mainThread())
    }


}
