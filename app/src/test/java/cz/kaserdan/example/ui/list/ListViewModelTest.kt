package cz.kaserdan.example.ui.list

import cz.kaserdan.example.model.entity.TransactionInfo
import cz.kaserdan.example.model.entity.TransactionItem
import cz.kaserdan.example.model.repository.TransactionRepository
import cz.kaserdan.example.navigation.NavigationRouter
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.Assert.*
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.junit.runners.model.Statement
import java.io.IOError
import java.io.IOException


@RunWith()
class ListViewModelTest {

    @Rule
    val rxTestRule = TestRule { base, d ->
        object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxJavaPlugins.setIoSchedulerHandler { scheduler -> Schedulers.trampoline() }
                RxJavaPlugins.setComputationSchedulerHandler { scheduler -> Schedulers.trampoline() }
                RxJavaPlugins.setNewThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }

                try {
                    base.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
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
        viewModel.stateOutput.test().assertValue { it.isError }

    }


}