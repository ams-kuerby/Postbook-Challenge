package de.adessomobile.postbookchallenge.ui.postcomments

import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val postCommentsKoinModule = module {

    viewModel { PostCommentsViewModel(get(), get()) }

    single { PostCommentsInteractorImpl(get()) as PostCommentsInteractor }
}