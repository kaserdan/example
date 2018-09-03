package cz.kaserdan.example.model.entity

data class TransactionInfo(val contraAccount: Account?) {

    data class Account(val accountNumber: String, val accountName: String, val bankCode: String)

}