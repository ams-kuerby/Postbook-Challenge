package de.adessomobile.postbookchallenge.base

import androidx.lifecycle.ViewModel
import de.adessomobile.postbookchallenge.base.coroutines.CoroutineContextProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * [ViewModel] to be used as a parent class for all ViewModels in this app. Implements [CoroutineScope] to be able to
 * cancel all [Job]s when [onCleared] is called.
 *
 * See https://discuss.kotlinlang.org/t/using-coroutines-scoped-to-viewmodel-for-io/10327
 */
abstract class BaseViewModel(
    protected val coroutineContextProvider: CoroutineContextProvider
) : ViewModel(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = coroutineContextProvider.ui + job

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
