package de.adessomobile.postbookchallenge.data.local

/**
 * Interface to save the favored posts.
 */
interface FavoredPostPersistence {

    /**
     * Return all IDs of favored posts.
     */
    suspend fun getFavoredPostIds(): Set<Int>

    /**
     * Favor the post with the given postId.
     */
    suspend fun favor(postId: Int)

    /**
     * Unfavor the post with the given postId.
     */
    suspend fun unfavor(postId: Int)
}