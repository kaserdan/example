package cz.kaserdan.example.ui.detail

import cz.kaserdan.example.model.entity.TransactionInfo
import cz.kaserdan.example.model.entity.TransactionItem
import cz.kaserdan.example.ui.MviResult

sealed class DetailActionResult : MviResult<DetailViewState>() {

    sealed class LoadItems : DetailActionResult() {

        data class Loading(val transactionItem: TransactionItem) : LoadItems() {

            override fun reduce(previousState: DetailViewState): DetailViewState =
                    previousState.copy(isProgress = true, isError = false, item = transactionItem)

        }

        object Error : LoadItems() {

            override fun reduce(previousState: DetailViewState): DetailViewState =
                    previousState.copy(isProgress = false, isError = true)

        }

        data class Success(val info: TransactionInfo) : LoadItems() {

            override fun reduce(previousState: DetailViewState): DetailViewState =
                    previousState.copy(isProgress = false, isError = false, info = info)

        }

    }


}