package de.adessomobile.postbookchallenge.repository.models

/**
 * DomainModel for a post.
 */
data class PostDomainModel(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
    val favored: Boolean
)