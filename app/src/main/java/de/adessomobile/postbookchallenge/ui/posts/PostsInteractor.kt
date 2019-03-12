package de.adessomobile.postbookchallenge.ui.posts

import de.adessomobile.postbookchallenge.repository.models.PostDomainModel

/**
 * Interactor to bundle access to Repositories
 */
interface PostsInteractor {

    /**
     * Get a list of all posts of the user with the given [userId].
     */
    suspend fun getPosts(userId: Int): List<PostDomainModel>
}