package de.adessomobile.postbookchallenge.ui.posts

import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val postsKoinModule = module {

    viewModel { PostsViewModel(get(), get()) }

    single { PostsInteractorImpl(get()) as PostsInteractor }
}