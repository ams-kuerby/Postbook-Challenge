package de.adessomobile.postbookchallenge.base.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class MockCoroutineContextProvider : CoroutineContextProvider {

    override val ui: CoroutineContext by lazy { Dispatchers.Unconfined }
    override val io: CoroutineContext by lazy { Dispatchers.Unconfined }
}