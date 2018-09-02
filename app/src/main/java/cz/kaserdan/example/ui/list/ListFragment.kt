package cz.kaserdan.example.ui.list

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import cz.kaserdan.example.R
import cz.kaserdan.example.di.Injectable
import cz.kaserdan.example.model.entity.TransactionItem
import cz.kaserdan.example.ui.ViewModelFactory
import kotlinx.android.synthetic.main.list_fragment.*
import javax.inject.Inject

class ListFragment : Fragment(), Injectable {

    @Inject
    protected lateinit var viewModelFactory: ViewModelFactory

    val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(ListViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.list_fragment, container, false)

    override fun onStart() {
        super.onStart()
        RxView.clicks(reloadBtn).map { TransactionItem.TransactionFilter() }.subscribe(viewModel.loadTransactionsFiltered)
    }

    companion object {
        fun newInstance() = ListFragment()
    }

}
