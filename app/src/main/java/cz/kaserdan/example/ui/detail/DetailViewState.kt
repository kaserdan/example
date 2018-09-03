package cz.kaserdan.example.ui.detail

import cz.kaserdan.example.model.entity.TransactionInfo
import cz.kaserdan.example.model.entity.TransactionItem
import cz.kaserdan.example.ui.ViewState

data class DetailViewState(
        val item: TransactionItem? = null, val info: TransactionInfo = TransactionInfo(null),
        val isError: Boolean = false, val isProgress: Boolean = false
) : ViewState