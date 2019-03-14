package de.adessomobile.postbookchallenge.ui.postcomments

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import de.adessomobile.postbookchallenge.base.BaseViewModel
import de.adessomobile.postbookchallenge.base.coroutines.CoroutineContextProvider
import de.adessomobile.postbookchallenge.base.livedata.refresh
import de.adessomobile.postbookchallenge.repository.models.PostDomainModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Architecture Component ViewModel for the PostCommentsActivity
 */
class PostCommentsViewModel(
    private val postCommentsInteractor: PostCommentsInteractor,
    coroutineContextProvider: CoroutineContextProvider
) : BaseViewModel(coroutineContextProvider) {

    /**
     * The post to display
     */
    private val post = MutableLiveData<PostCommentsPresentationModel.PostPresentationModel>()

    /**
     * All comments for the post.
     */
    private val comments = MutableLiveData<List<PostCommentsPresentationModel.CommentPresentationModel>>()

    /**
     * The post and its comments combined.
     */
    val items = MediatorLiveData<List<PostCommentsPresentationModel>>()
        .apply {
            addSource(comments) {
                value = combineModels()
            }
            addSource(post) {
                value = combineModels()
            }
        }

    private fun combineModels(): List<PostCommentsPresentationModel> {
        val post = post.value
        val comments = comments.value

        if (post == null || comments == null) {
            return emptyList()
        }

        return listOf(post) + comments
    }

    /**
     * Load the post and its comments with the given [postId].
     */
    fun loadData(postId: Int) {
        launch {
            val deferredComments = async(coroutineContextProvider.io) {
                postCommentsInteractor.getComments(postId)
                    .map {
                        PostCommentsPresentationModel.CommentPresentationModel(it.id, it.name, it.email, it.body)
                    }
            }
            val deferredPost = async(coroutineContextProvider.io) {
                postCommentsInteractor.getPost(postId)
                    .let {
                        PostCommentsPresentationModel.PostPresentationModel(
                            it.userId,
                            it.id,
                            it.title,
                            it.body,
                            it.favored
                        )
                    }
            }

            comments.value = deferredComments.await()
            post.value = deferredPost.await()
        }
    }

    /**
     * Handle a click on the favorite button of the post.
     *
     * @param favored the new favorite state.
     */
    fun onPostFavoriteClick(favored: Boolean) {
        launch {
            withContext(coroutineContextProvider.io) {
                post.value?.let {
                    if (favored != it.favored) {
                        it.favored = favored
                        postCommentsInteractor.updatePost(
                            PostDomainModel(it.userId, it.id, it.title, it.body, favored)
                        )
                    }
                }
            }
        }
        post.refresh()
    }
}