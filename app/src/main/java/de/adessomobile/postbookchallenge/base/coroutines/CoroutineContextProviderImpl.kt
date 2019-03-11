package de.adessomobile.postbookchallenge.base.coroutines

import kotlinx.coroutines.Dispatchers

/**
 * Implementation of the [CoroutineContextProvider] using the main dispatcher for UI bound tasks and the IO dispatcher
 * for IO bound tasks.
 */
class CoroutineContextProviderImpl : CoroutineContextProvider {

    override val ui by lazy { Dispatchers.Main }

    override val io by lazy { Dispatchers.IO }
}
