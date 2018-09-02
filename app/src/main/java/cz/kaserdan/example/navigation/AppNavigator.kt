package cz.kaserdan.example.navigation

import android.support.v4.app.Fragment
import cz.kaserdan.example.model.entity.TransactionItem
import cz.kaserdan.example.ui.detail.DetailFragment
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.processors.UnicastProcessor
import javax.inject.Inject

class AppNavigator @Inject constructor() : NavigationCommander, NavigationRouter {

    private val fragmentEvents: UnicastProcessor<Fragment> = UnicastProcessor.create()

    override val replaceFragment: Observable<Fragment>
        get() = fragmentEvents.toObservable()

    override fun showTransactionDetail(transactionItem: TransactionItem): Completable =
        Completable.fromAction { fragmentEvents.onNext(DetailFragment.newInstance(transactionItem)) }


}