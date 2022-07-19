package kz.tredo.mvi

sealed class DataState<out T> {
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val exception: Exception) : DataState<Nothing>()

    object ShowLoading : DataState<Nothing>()
    object HideLoading : DataState<Nothing>()

    object None : DataState<Nothing>()
}
