package de.adessomobile.postbookchallenge.ui.posts

import de.adessomobile.postbookchallenge.repository.PostsRepository
import de.adessomobile.postbookchallenge.repository.models.PostDomainModel

class PostsInteractorImpl(
    private val postsRepository: PostsRepository
) : PostsInteractor {

    override suspend fun getPosts(userId: Int) = postsRepository.listPosts(userId)

    override suspend fun updatePost(post: PostDomainModel) {
        postsRepository.updatePost(post)
    }
}