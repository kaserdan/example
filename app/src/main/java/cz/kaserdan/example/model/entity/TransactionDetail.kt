package cz.kaserdan.example.model.entity

/**
 * Created by egold
 */
data class TransactionDetail(val contraAccount: Account) {

    data class Account(val accountNumber: String, val accountName: String, val bankCode: String)

}