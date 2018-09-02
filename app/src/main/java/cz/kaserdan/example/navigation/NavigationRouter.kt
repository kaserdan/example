package cz.kaserdan.example.navigation

import cz.kaserdan.example.model.entity.TransactionItem
import io.reactivex.Completable

interface NavigationRouter {

    fun showTransactionDetail(transactionItem: TransactionItem): Completable

}