package cz.kaserdan.example.model.entity

/**
 * Created by egold
 */
data class TransactionItem(val id: Int, val amountInAccountCurrency: Int, val direction: Direction) {

    enum class Direction {
        INCOMING, OUTGOING
    }

    class TransactionFilter(val direction: Direction?)

}