package cz.kaserdan.example.ui.list

import cz.kaserdan.example.model.entity.TransactionInfo
import cz.kaserdan.example.model.entity.TransactionItem
import cz.kaserdan.example.model.repository.TransactionRepository
import cz.kaserdan.example.navigation.NavigationRouter
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import java.io.IOException


class ListViewModelTest {

    @Before
    fun setupClass() {
        RxJavaPlugins.setIoSchedulerHandler { _ -> Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { _ -> Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { _ -> Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> Schedulers.trampoline() }
    }

    @Test
    fun testErrorFromRepository() {
        val navigator = object : NavigationRouter {
            override fun showTransactionDetail(transactionItem: TransactionItem): Completable = Completable.complete()
        }
        val repository = object : TransactionRepository {
            override fun fetchTransactions(filter: TransactionItem.TransactionFilter?): Observable<List<TransactionItem>> =
                    Observable.error(IOException("Test exception"))

            override fun fetchTransactionInfo(transactionId: Int): Observable<TransactionInfo> = Observable.empty()

        }
        val listActionProcessorHolder = ListActionProcessorHolder(repository, navigator)
        val viewModel = ListViewModel(listActionProcessorHolder)
        viewModel.loadTransactionsFiltered.accept(TransactionItem.TransactionFilter())
        viewModel.stateOutput.test()
                .awaitCount(1)
                .assertValue{it.isError}
    }

    @Test
    fun testNavigation() {
        val itemToNavigate = TransactionItem(0,0,TransactionItem.Direction.OUTGOING)
        var navigatedToItem = false
        val navigator = object : NavigationRouter {
            override fun showTransactionDetail(transactionItem: TransactionItem): Completable =
                    Completable.fromAction { navigatedToItem = itemToNavigate == transactionItem }
        }
        val repository = object : TransactionRepository {
            override fun fetchTransactions(filter: TransactionItem.TransactionFilter?): Observable<List<TransactionItem>> =
                    Observable.error(IOException("Test exception"))

            override fun fetchTransactionInfo(transactionId: Int): Observable<TransactionInfo> = Observable.empty()

        }
        val listActionProcessorHolder = ListActionProcessorHolder(repository, navigator)
        val viewModel = ListViewModel(listActionProcessorHolder)
        viewModel.itemClick.accept(itemToNavigate)
        assert(navigatedToItem)
    }

}