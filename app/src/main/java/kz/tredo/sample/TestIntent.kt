package kz.tredo.sample

import kz.tredo.mvi.BaseIntent

sealed class TestIntent : BaseIntent() {
    object GetUsers : TestIntent()
    data class RemoveUser(val user: User) : TestIntent()
}
