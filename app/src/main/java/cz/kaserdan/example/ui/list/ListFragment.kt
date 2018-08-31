package cz.kaserdan.example.ui.list

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.kaserdan.example.R
import cz.kaserdan.example.di.Injectable
import cz.kaserdan.example.ui.ViewModelFactory
import javax.inject.Inject

class ListFragment : Fragment(), Injectable {

    @Inject
    protected lateinit var viewModelFactory: ViewModelFactory

    val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(ListViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.list_fragment, container, false)

    override fun onStart() {
        super.onStart()

    }

    companion object {
        fun newInstance() = ListFragment()
    }

}
