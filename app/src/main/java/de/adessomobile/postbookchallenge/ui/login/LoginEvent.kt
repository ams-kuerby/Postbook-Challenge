package de.adessomobile.postbookchallenge.ui.login

/**
 * Sealed class used for event propagation from the [LoginViewModel] to the [LoginActivity].
 */
sealed class LoginEvent {

    /**
     * Event indicating that the user posts with the given [userId] shall be shown.
     */
    data class ShowUserPosts(
        val userId: Int
    ) : LoginEvent()
}