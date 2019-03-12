package de.adessomobile.postbookchallenge.base

import android.app.Application
import de.adessomobile.postbookchallenge.BuildConfig
import de.adessomobile.postbookchallenge.base.coroutines.coroutineKoinModule
import de.adessomobile.postbookchallenge.data.dataKoinModule
import de.adessomobile.postbookchallenge.repository.repositoryKoinModule
import de.adessomobile.postbookchallenge.ui.login.loginKoinModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initTimber()
        initKoin()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initKoin() {
        startKoin(
            this, listOf(
                coroutineKoinModule,
                dataKoinModule,
                loginKoinModule,
                repositoryKoinModule
            )
        )
    }
}