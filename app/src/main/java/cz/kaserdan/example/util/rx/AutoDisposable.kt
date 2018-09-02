package cz.kaserdan.example.util.rx


import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableContainer
import java.lang.ref.WeakReference
import java.util.concurrent.ConcurrentHashMap

class AutoDisposable(lifecycle: Lifecycle) : LifecycleObserver, DisposableContainer {

    /**
     * Observed lifecycle reference
     */
    private val lifecycleRef = WeakReference(lifecycle)
    /**
     * CompositeDisposable for tracking the subscriptions.
     */
    private val eventLifecycleDisposables: MutableMap<Lifecycle.Event, CompositeDisposable> = ConcurrentHashMap()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onEventDestroy() {
        checkAndDispose(Lifecycle.Event.ON_DESTROY)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onEventPause() {
        checkAndDispose(Lifecycle.Event.ON_PAUSE)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onEventStop() {
        checkAndDispose(Lifecycle.Event.ON_STOP)
    }

    /**
     * Checks if the event matching the provided event for disposal.
     */
    private fun checkAndDispose(occurredEvent: Lifecycle.Event) {
        eventLifecycleDisposables[occurredEvent]?.let {
            it.clear()
            // remove composite disposable if last one
            if (it.size() == 0) {
                eventLifecycleDisposables.remove(occurredEvent)
            }
        }
        // remove last observer
        if (eventLifecycleDisposables.isEmpty()) {
            lifecycleRef.get()?.removeObserver(this)
        }
    }

    /**
     * Attaches subscription disposable on Observable's subscribe, for future disposal when event happens.
     */
    override fun add(disposable: Disposable): Boolean {
        // on every subscription at current state dispose on down event
        return add(disposable, upDownEvent() ?: Lifecycle.Event.ON_ANY)
    }

    /**
     * Attaches subscription disposable on Observable's subscribe, for future disposal when event happens.
     */
    fun add(disposable: Disposable, observedEvent: Lifecycle.Event): Boolean {
        // on every subscription at current state dispose on down event
        var compositeDisposable: CompositeDisposable? = eventLifecycleDisposables[observedEvent]
        if (compositeDisposable == null) {
            if (eventLifecycleDisposables.isEmpty()) {
                lifecycleRef.get()?.addObserver(this)
            }
            compositeDisposable = CompositeDisposable()
            eventLifecycleDisposables[observedEvent] = compositeDisposable
        }
        return compositeDisposable.add(disposable)
    }

    override fun remove(d: Disposable): Boolean {
        return eventLifecycleDisposables.values.map { it.remove(d) }.reduce { acc, b -> acc || b }
    }

    override fun delete(d: Disposable): Boolean {
        return eventLifecycleDisposables.values.map { it.delete(d) }.reduce { acc, b -> acc || b }
    }

    /**
     * Copied from LifecycleRegistry
     */
    private fun upDownEvent(): Lifecycle.Event? {
        val state = lifecycleRef.get()?.currentState?.ordinal ?: return null
        return when (state) {
            1 //Lifecycle.State.INITIALIZED
            -> Lifecycle.Event.ON_DESTROY
            2 //Lifecycle.State.CREATED
            -> Lifecycle.Event.ON_STOP
            3 //Lifecycle.State.STARTED
            -> Lifecycle.Event.ON_PAUSE
            else -> null
        }
    }

    companion object {

        private val TAG = AutoDisposable::class.java.simpleName

        fun Disposable.addTo(disposableContainer: DisposableContainer) {
            disposableContainer.add(this)
        }

    }
}