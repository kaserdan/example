package cz.kaserdan.example.ui.list

import cz.kaserdan.example.ui.MviResult

sealed class ListActionResult : MviResult<ListViewState>() {

    sealed class LoadItems : ListActionResult() {

        object Loading : LoadItems() {

            override fun reduce(previousState: ListViewState): ListViewState {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }

        object Error : LoadItems() {

            override fun reduce(previousState: ListViewState): ListViewState {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }

        object Success : LoadItems() {

            override fun reduce(previousState: ListViewState): ListViewState {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }

    }


}