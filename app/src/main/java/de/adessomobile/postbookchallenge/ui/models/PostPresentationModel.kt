package de.adessomobile.postbookchallenge.ui.models

data class PostPresentationModel(
    val id: Int,
    val title: String,
    val body: String,
    var favored: Boolean
)