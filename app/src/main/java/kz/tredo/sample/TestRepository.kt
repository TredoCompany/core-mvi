package kz.tredo.sample

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kz.tredo.mvi.DataState

/**
 * Мы управляем ui state в репозиторий
 * Когда мы создаём свои exceptions наследуем от Exception()
 * */

interface TestRepository {
    suspend fun getUsers(): Flow<DataState<MutableList<User>>>
    suspend fun removeUser(user: User): Flow<DataState<Boolean>>
}

class TestRepositoryImpl : TestRepository {

    override suspend fun getUsers() = flow {
        emit(DataState.ShowLoading)
        try {
            delay(1500L)
            emit(
                DataState.Success(
                    mutableListOf(
                        User(1, "John"),
                        User(2, "David"),
                        User(3, "Sara"),
                        User(4, "Kevin"),
                        User(5, "Robert")
                    )
                )
            )
        } catch (exception: Exception) {
            emit(DataState.Error(exception))
        }
    }

    override suspend fun removeUser(user: User): Flow<DataState<Boolean>> = flow {
        emit(DataState.ShowLoading)
        try {
            delay(1500L)
            emit(DataState.Success(true))
        } catch (exception: Exception) {
            emit(DataState.Error(exception))
        }
    }
}