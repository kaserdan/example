package cz.kaserdan.example.ui.list

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import cz.kaserdan.example.R
import cz.kaserdan.example.model.entity.TransactionItem
import cz.kaserdan.example.ui.BaseFragment
import cz.kaserdan.example.util.rx.AutoDisposable.Companion.addTo
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : BaseFragment<ListViewState, ListViewModel>() {

    override val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(ListViewModel::class.java) }

    private val adapter = TransactionsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBarView = progressBar
        reloadBtnView = reloadBtn
        recyclerView.addItemDecoration(DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL)
                .apply { ContextCompat.getDrawable(view.context, R.drawable.divider_vertical)?.let { setDrawable(it) } })
        recyclerView.adapter = adapter
    }

    override fun setIntents() {
        adapter.itemClicks.subscribe(viewModel.itemClick)
                .addTo(disposableBag)
        val reloadClick = RxView.clicks(reloadBtn)
        Observable.merge(RxView.clicks(filterAllTV), reloadClick.filter { filterAllTV.isSelected })
                .map { TransactionItem.TransactionFilter() }
                .subscribe(viewModel.loadTransactionsFiltered)
                .addTo(disposableBag)
        Observable.merge(RxView.clicks(filterIncomingTV), reloadClick.filter { filterIncomingTV.isSelected })
                .map { TransactionItem.TransactionFilter(TransactionItem.Direction.INCOMING) }
                .subscribe(viewModel.loadTransactionsFiltered)
                .addTo(disposableBag)
        Observable.merge(RxView.clicks(filterOutgoingTV), reloadClick.filter { filterOutgoingTV.isSelected })
                .map { TransactionItem.TransactionFilter(TransactionItem.Direction.OUTGOING) }
                .subscribe(viewModel.loadTransactionsFiltered)
                .addTo(disposableBag)
    }

    override fun startRendering(state: Observable<ListViewState>) {
        state.map { it.isError }.distinctUntilChanged().subscribe { renderError(it) }.addTo(disposableBag)
        state.map { it.items }.distinctUntilChanged().subscribe { renderItems(it) }.addTo(disposableBag)
        state.map { it.isProgress }.distinctUntilChanged().subscribe { renderProgress(it) }.addTo(disposableBag)
        state.map { it.selectedFilter }.distinctUntilChanged().subscribe { renderFilter(it) }.addTo(disposableBag)
    }

    private fun renderFilter(filter: TransactionItem.TransactionFilter) {
        filterAllTV.isSelected = filter.direction == null
        filterIncomingTV.isSelected = filter.direction == TransactionItem.Direction.INCOMING
        filterOutgoingTV.isSelected = filter.direction == TransactionItem.Direction.OUTGOING
    }

    private fun renderItems(items: List<TransactionItem>) {
        adapter.changeItems(items)
    }

    companion object {
        fun newInstance() = ListFragment()
    }

}
