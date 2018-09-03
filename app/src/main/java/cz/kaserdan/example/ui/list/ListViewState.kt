package cz.kaserdan.example.ui.list

import cz.kaserdan.example.model.entity.TransactionItem
import cz.kaserdan.example.ui.ViewState

data class ListViewState(
        val items: List<TransactionItem> = arrayListOf(), val isError: Boolean = false, val isProgress: Boolean = false,
        val selectedFilter: TransactionItem.TransactionFilter = TransactionItem.TransactionFilter()
) : ViewState