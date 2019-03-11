package de.adessomobile.postbookchallenge.base

import android.app.Application
import de.adessomobile.postbookchallenge.client.clientKoinModule
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin(
            this, listOf(
                clientKoinModule
            )
        )
    }
}