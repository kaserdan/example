package cz.kaserdan.example.ui

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel<T : ViewState> : ViewModel() {

    abstract val stateOutput: Observable<T>

    private val disposableBag = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposableBag.dispose()
    }

    protected fun Disposable.toDisposeBag() {
        disposableBag.add(this)
    }

}
