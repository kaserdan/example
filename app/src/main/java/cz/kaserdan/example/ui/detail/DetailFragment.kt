package cz.kaserdan.example.ui.detail

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import cz.kaserdan.example.R
import cz.kaserdan.example.model.entity.TransactionInfo
import cz.kaserdan.example.model.entity.TransactionItem
import cz.kaserdan.example.ui.BaseFragment
import io.reactivex.Observable
import cz.kaserdan.example.util.rx.AutoDisposable.Companion.addTo
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.transaction_body.*

class DetailFragment : BaseFragment<DetailViewState, DetailViewModel>() {

    override val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel::class.java) }

    private val transactionItem by lazy { requireNotNull(arguments?.getParcelable<TransactionItem>(TransactionItem.TAG)) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBarView = progressBar
        reloadBtnView = reloadBtn
        if (savedInstanceState == null) {
            viewModel.loadTransactionInfo.accept(transactionItem)
        }
        renderItem(transactionItem)
    }

    override fun setIntents() {
        RxView.clicks(reloadBtn).map { transactionItem }.subscribe(viewModel.loadTransactionInfo).addTo(disposableBag)
    }

    override fun startRendering(state: Observable<DetailViewState>) {
        state.map { it.isError }.distinctUntilChanged().subscribe { renderError(it) }.addTo(disposableBag)
        state.map { it.isProgress }.distinctUntilChanged().subscribe { renderProgress(it) }.addTo(disposableBag)
        state.map { it.info }.distinctUntilChanged().subscribe { renderInfo(it) }.addTo(disposableBag)
    }

    private fun renderInfo(info: TransactionInfo) {
        infoVG.visibility = if (info.contraAccount == null) View.GONE else View.VISIBLE
        accountNumberTV.text = info.contraAccount?.accountNumber
        accountNameTV.text = info.contraAccount?.accountName
        bankCodeTV.text = info.contraAccount?.bankCode
    }

    private fun renderItem(item: TransactionItem) {
        iconIV.setImageResource(if (item.direction == TransactionItem.Direction.INCOMING) R.drawable.ic_incoming else R.drawable.ic_outgoing)
        priceTV.text = String.format("%d Kč", item.amountInAccountCurrency)
        directionTV.text = item.direction.name.toLowerCase()
    }

    companion object {

        fun newInstance(item: TransactionItem): DetailFragment =
                DetailFragment().apply { arguments = Bundle().apply { putParcelable(TransactionItem.TAG, item) } }

    }

}