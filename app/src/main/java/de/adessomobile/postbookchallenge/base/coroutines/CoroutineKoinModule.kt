package de.adessomobile.postbookchallenge.base.coroutines

import org.koin.dsl.module.module

val coroutineKoinModule = module {

    single { CoroutineContextProviderImpl() as CoroutineContextProvider }
}
