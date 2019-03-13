package de.adessomobile.postbookchallenge.base.livedata

import androidx.lifecycle.MutableLiveData

/**
 * Extension function to refresh a MutableLiveData.
 * Normally ,when the contents of the object are changed, the observers will not be notified of the change.
 */
fun <T> MutableLiveData<T>.refresh() {
    this.value = this.value
}