package cz.kaserdan.example.ui.detail

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


class DetailViewModelTest {

    @Before
    fun setupClass() {
        RxJavaPlugins.setIoSchedulerHandler { _ -> Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { _ -> Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { _ -> Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> Schedulers.trampoline() }
    }

    @Test
    fun testErrorFromRepository() {
        val item = TransactionItem(0, 0, TransactionItem.Direction.OUTGOING)
        val repository = object : TransactionRepository {
            override fun fetchTransactions(filter: TransactionItem.TransactionFilter?): Observable<List<TransactionItem>> =
                    Observable.empty()

            override fun fetchTransactionInfo(transactionId: Int): Observable<TransactionInfo> =
                    Observable.error(IOException("Test exception"))

        }
        val listActionProcessorHolder = DetailActionProcessorHolder(repository)
        val viewModel = DetailViewModel(listActionProcessorHolder)
        viewModel.loadTransactionInfo.accept(item)
        viewModel.stateOutput.test()
                .awaitCount(1)
                .assertValue { it.isError }
    }

}