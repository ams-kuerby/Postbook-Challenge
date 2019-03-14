package de.adessomobile.postbookchallenge.ui.postcomments

import de.adessomobile.postbookchallenge.repository.models.CommentDomainModel
import de.adessomobile.postbookchallenge.repository.models.PostDomainModel

/**
 * Interactor to bundle access to Repositories
 */
interface PostCommentsInteractor {

    /**
     * Get the post with the given [postId].
     */
    suspend fun getPost(postId: Int): PostDomainModel

    /**
     * Update the given [post].
     */
    suspend fun updatePost(post: PostDomainModel)

    /**
     * Get a list of all comments of the post with the given [postId].
     */
    suspend fun getComments(postId: Int): List<CommentDomainModel>
}