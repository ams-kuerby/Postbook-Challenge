package de.adessomobile.postbookchallenge.ui.login

import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val loginKoinModule = module {

    viewModel { LoginViewModel(get()) }
}