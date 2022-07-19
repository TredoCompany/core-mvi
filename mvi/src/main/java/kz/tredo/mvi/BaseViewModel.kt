package kz.tredo.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class BaseViewModel<I : BaseIntent> : ViewModel() {

    private var _intent: BaseIntent? = null

    protected val event: I
        get() = _intent as I

    abstract fun onTriggerEvent(eventType: I)

    protected fun launchInScope(block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch {
            block()
        }

    protected fun <T> Flow<T>.collectInScope(collector: suspend (T) -> Unit) {
        launchInScope { collectLatest(collector) }
    }
}