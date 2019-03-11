package de.adessomobile.postbookchallenge.repository.models

/**
 * DomainModel for a comment.
 */
data class CommentDomainModel(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)