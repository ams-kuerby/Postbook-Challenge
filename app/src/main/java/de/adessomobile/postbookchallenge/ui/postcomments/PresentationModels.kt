package de.adessomobile.postbookchallenge.ui.postcomments

sealed class PostCommentsPresentationModel {

    data class PostPresentationModel(
        val userId: Int,
        val id: Int,
        val title: String,
        val body: String,
        var favored: Boolean
    ) : PostCommentsPresentationModel()

    data class CommentPresentationModel(
        val id: Int,
        val name: String,
        val email: String,
        val body: String
    ) : PostCommentsPresentationModel()
}