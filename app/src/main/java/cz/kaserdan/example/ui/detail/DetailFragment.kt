package cz.kaserdan.example.ui.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import cz.kaserdan.example.model.entity.TransactionItem

class DetailFragment : Fragment() {


    companion object {

        fun newInstance(item: TransactionItem): DetailFragment =
            DetailFragment().apply { arguments = Bundle().apply { putParcelable(TransactionItem.TAG, item) } }

    }

}