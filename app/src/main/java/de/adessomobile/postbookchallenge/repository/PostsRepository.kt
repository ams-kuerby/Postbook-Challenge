package de.adessomobile.postbookchallenge.repository

import de.adessomobile.postbookchallenge.repository.models.CommentDomainModel
import de.adessomobile.postbookchallenge.repository.models.PostDomainModel

/**
 * Repository to provide posts and comments.
 */
interface PostsRepository {

    /**
     * Get a list of all posts of the user with the given [userId].
     */
    suspend fun listPosts(userId: Int): List<PostDomainModel>

    /**
     * Update the given [post].
     */
    suspend fun updatePost(post: PostDomainModel)

    /**
     * Get a list of all comments for a post with the given [postId].
     */
    suspend fun listComments(postId: Int): List<CommentDomainModel>
}