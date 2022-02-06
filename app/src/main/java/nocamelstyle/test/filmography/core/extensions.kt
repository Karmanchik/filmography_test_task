package nocamelstyle.test.filmography.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

fun <T> Flow<T>.convertToFlowViewState(
    dispatcher: CoroutineDispatcher = Dispatchers.Default
): Flow<ViewState<T>> {
    return this
        .map { list ->
            ViewState(status = Status.SUCCESS, data = list)
        }
        .catch { cause: Throwable ->
            emitAll(flowOf(ViewState(Status.ERROR, error = cause)))
        }
        .flowOn(dispatcher)
}