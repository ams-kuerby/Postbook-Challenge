package de.adessomobile.postbookchallenge.ui.login

import de.adessomobile.postbookchallenge.base.BaseViewModel
import de.adessomobile.postbookchallenge.base.coroutines.CoroutineContextProvider
import de.adessomobile.postbookchallenge.base.livedata.SingleLiveEvent

/**
 * Architecture Component ViewModel for the LoginActivity
 */
class LoginViewModel(
    coroutineContextProvider: CoroutineContextProvider
) : BaseViewModel(coroutineContextProvider) {

    var userId: String = ""

    val events = SingleLiveEvent<LoginEvent>()

    fun loginClicked() {
        events.value = LoginEvent.ShowUserPosts(userId.toInt())
    }
}