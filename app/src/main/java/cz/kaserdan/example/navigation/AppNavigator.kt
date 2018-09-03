package cz.kaserdan.example.navigation

import android.support.v4.app.Fragment
import cz.kaserdan.example.model.entity.TransactionItem
import cz.kaserdan.example.ui.detail.DetailFragment
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNavigator @Inject constructor() : NavigationCommander, NavigationRouter {

    private val fragmentEvents: PublishSubject<Fragment> = PublishSubject.create()

    override val replaceFragment: Observable<Fragment>
        get() = fragmentEvents.hide()

    override fun showTransactionDetail(transactionItem: TransactionItem): Completable =
        Completable.fromAction { fragmentEvents.onNext(DetailFragment.newInstance(transactionItem)) }


}