package cz.kaserdan.example.ui.list

import cz.kaserdan.example.model.entity.TransactionItem
import cz.kaserdan.example.ui.MviResult

sealed class ListActionResult : MviResult<ListViewState>() {

    sealed class LoadItems : ListActionResult() {

        data class Loading(val filter: TransactionItem.TransactionFilter) : LoadItems() {

            override fun reduce(previousState: ListViewState): ListViewState =
                previousState.copy(isProgress = true, isError = false, items = arrayListOf(), selectedFilter = filter)

        }

        object Error : LoadItems() {

            override fun reduce(previousState: ListViewState): ListViewState =
                previousState.copy(isProgress = false, isError = true)

        }

        data class Success(val items: List<TransactionItem>) : LoadItems() {

            override fun reduce(previousState: ListViewState): ListViewState =
                previousState.copy(isProgress = false, isError = false, items = items)

        }

    }


}