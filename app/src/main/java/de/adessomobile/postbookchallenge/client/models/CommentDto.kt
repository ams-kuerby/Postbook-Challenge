package de.adessomobile.postbookchallenge.client.models

/**
 * DTO for a comment delivered by the backend.
 */
data class CommentDto(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)