package cz.kaserdan.example.ui.detail

import com.jakewharton.rxrelay2.PublishRelay
import cz.kaserdan.example.model.entity.TransactionItem
import cz.kaserdan.example.ui.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import javax.inject.Inject

class DetailViewModel @Inject constructor(processor: DetailActionProcessorHolder) : BaseViewModel<DetailViewState>() {

    val loadTransactionInfo: Consumer<TransactionItem>

    override val stateOutput: Observable<DetailViewState>

    init {
        val processedInputs = ArrayList<Observable<DetailActionResult>>()
        loadTransactionInfo = PublishRelay.create<TransactionItem>().apply {
            processedInputs.add(compose(processor.loadTransactionsProcessor))
        }
        stateOutput = Observable.merge(processedInputs)
                .scan(DetailViewState()) { previousState, result -> result.reduce(previousState) }
                .replay(1)
                .autoConnect(0) { it.toDisposeBag() }
                .observeOn(AndroidSchedulers.mainThread())
    }


}
