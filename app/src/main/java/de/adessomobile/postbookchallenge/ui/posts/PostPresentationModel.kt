package de.adessomobile.postbookchallenge.ui.posts

data class PostPresentationModel(
    val id: Int,
    val title: String,
    val body: String,
    var favored: Boolean
)