package de.adessomobile.postbookchallenge.ui.postcomments

import de.adessomobile.postbookchallenge.repository.PostsRepository
import de.adessomobile.postbookchallenge.repository.models.PostDomainModel

/**
 * Implementation for the PostCommentsInteractor
 */
class PostCommentsInteractorImpl(
    private val postsRepository: PostsRepository
) : PostCommentsInteractor {

    override suspend fun getPost(postId: Int) = postsRepository.getPost(postId)

    override suspend fun updatePost(post: PostDomainModel) {
        postsRepository.updatePost(post)
    }

    override suspend fun getComments(postId: Int) = postsRepository.listComments(postId)
}