package de.adessomobile.postbookchallenge.ui.posts

import de.adessomobile.postbookchallenge.repository.PostsRepository

class PostsInteractorImpl(
    private val postsRepository: PostsRepository
) : PostsInteractor {

    override suspend fun getPosts(userId: Int) = postsRepository.listPosts(userId)
}