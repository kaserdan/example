package cz.kaserdan.example.ui

import android.support.v4.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import cz.kaserdan.example.di.Injectable
import cz.kaserdan.example.util.rx.AutoDisposable
import io.reactivex.Observable
import javax.inject.Inject

abstract class BaseFragment<T: ViewState, R: BaseViewModel<T>> : Fragment(), Injectable {

    protected lateinit var progressBarView: ProgressBar

    protected lateinit var reloadBtnView: Button

    protected abstract val viewModel: R

    @Inject
    protected lateinit var viewModelFactory: ViewModelFactory

    protected val disposableBag = AutoDisposable(lifecycle)

    override fun onStart() {
        super.onStart()
        setIntents()
        startRendering(viewModel.stateOutput)
    }

    protected fun renderProgress(isProgress: Boolean) {
        progressBarView.visibility = if (isProgress) View.VISIBLE else View.GONE
    }

    protected fun renderError(isError: Boolean) {
        reloadBtnView.visibility = if (isError) View.VISIBLE else View.GONE
    }

    protected abstract fun setIntents()

    protected abstract fun startRendering(state: Observable<T>)

}