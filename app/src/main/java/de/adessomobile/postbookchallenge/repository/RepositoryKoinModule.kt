package de.adessomobile.postbookchallenge.repository

import org.koin.dsl.module.module

val repositoryKoinModule = module {

    single { PostsRepositoryImpl(get(), get()) as PostsRepository }
}