package de.adessomobile.postbookchallenge.ui.postcomments

import org.koin.dsl.module.module

val postCommentsKoinModule = module {

    single { PostCommentsInteractorImpl(get()) as PostCommentsInteractor }
}