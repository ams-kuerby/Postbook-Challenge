package de.adessomobile.postbookchallenge.data.client.models

/**
 * DTO for a post delivered by the backend.
 */
data class PostDto(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)