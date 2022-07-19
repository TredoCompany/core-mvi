package kz.tredo.sample

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kz.tredo.mvi.BaseViewModel
import kz.tredo.mvi.DataState

class TestViewModel : BaseViewModel<TestIntent>() {

    private val testRepository: TestRepository = TestRepositoryImpl()

    private val _getUsersState = MutableStateFlow<DataState<MutableList<User>>>(DataState.None)
    val getUsersState: StateFlow<DataState<MutableList<User>>> = _getUsersState

    private val _removeUserState = MutableStateFlow<DataState<Boolean>>(DataState.None)
    val removeUserState: StateFlow<DataState<Boolean>> = _removeUserState

    override fun onTriggerEvent(eventType: TestIntent) {
        launchInScope {
            when (eventType) {
                TestIntent.GetUsers -> {
                    getUsers()
                }
                is TestIntent.RemoveUser -> {
                    removeUser(user = eventType.user)
                }
                else -> {
                    // do  nothing
                }
            }
        }
    }

    private suspend fun getUsers() {
        testRepository.getUsers().collect {
            Log.e("USERS", it.toString())
            _getUsersState.emit(it)
        }
    }

    private suspend fun removeUser(user: User) {
        testRepository.removeUser(user).collect {
            _removeUserState.emit(it)
        }
    }
}