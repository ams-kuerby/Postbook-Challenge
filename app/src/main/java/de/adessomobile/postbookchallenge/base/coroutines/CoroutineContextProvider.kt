package de.adessomobile.postbookchallenge.base.coroutines

import kotlin.coroutines.CoroutineContext

/**
 * Interface to be used to provide a [CoroutineContext] to the launcher of a coroutine. Using this, the
 * [CoroutineContext] can be exchanged during testing.
 */
interface CoroutineContextProvider {

    /**
     * [CoroutineContext] for UI bound tasks.
     */
    val ui: CoroutineContext

    /**
     * [CoroutineContext] for IO bound tasks.
     */
    val io: CoroutineContext
}
