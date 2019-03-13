package de.adessomobile.postbookchallenge.ui.posts

/**
 * Sealed class used for event propagation from the [PostsViewModel] to the [PostsActivity].
 */
sealed class PostsEvent {

    /**
     * Event indicating that the comments for the post with the given [postId] shall be shown.
     */
    data class ShowPostComments(
        val postId: Int
    ) : PostsEvent()
}