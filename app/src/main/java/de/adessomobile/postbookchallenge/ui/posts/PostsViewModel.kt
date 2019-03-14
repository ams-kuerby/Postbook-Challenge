package de.adessomobile.postbookchallenge.ui.posts

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import de.adessomobile.postbookchallenge.base.BaseViewModel
import de.adessomobile.postbookchallenge.base.coroutines.CoroutineContextProvider
import de.adessomobile.postbookchallenge.base.livedata.SingleLiveEvent
import de.adessomobile.postbookchallenge.base.livedata.refresh
import de.adessomobile.postbookchallenge.repository.models.PostDomainModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Architecture Component ViewModel for the PostsActivity
 */
class PostsViewModel(
    private val postsInteractor: PostsInteractor,
    coroutineContextProvider: CoroutineContextProvider
) : BaseViewModel(coroutineContextProvider) {

    private var userId: Int? = null

    /**
     * All available posts for the user.
     */
    private val allPosts = MutableLiveData<List<PostPresentationModel>>()

    /**
     * The state whether all posts or only the favored posts should be shown.
     */
    val showOnlyFavoredPosts = MutableLiveData<Boolean>()

    /**
     * The posts filtered according to the state of showOnlyFavoredPosts
     */
    val posts = MediatorLiveData<List<PostPresentationModel>>()
        .apply {
            addSource(allPosts) {
                value = filterPosts()
            }
            addSource(showOnlyFavoredPosts) {
                value = filterPosts()
            }
        }

    /**
     * [SingleLiveEvent] used to post [PostsEvent]s to an interested observer.
     */
    val events = SingleLiveEvent<PostsEvent>()

    /**
     * Load the posts of the user with the given [userId].
     */
    fun loadPosts(userId: Int) {
        this.userId = userId
        launch {
            allPosts.value = withContext(coroutineContextProvider.io) {
                postsInteractor.getPosts(userId)
                    .map {
                        PostPresentationModel(
                            it.id,
                            it.title,
                            it.body,
                            it.favored
                        )
                    }
            }
        }
    }

    /**
     * Handle a click on the post with the given [postId]
     */
    fun onPostClick(postId: Int) {
        events.value = PostsEvent.ShowPostComments(postId)
    }

    /**
     * Handle a click on the favorite button of the post with the given [postId].
     *
     * @param favored the new favorite state.
     */
    fun onPostFavoriteClick(postId: Int, favored: Boolean) {
        launch {
            withContext(coroutineContextProvider.io) {
                userId?.let { userId ->
                    allPosts.value?.find { it.id == postId }?.let {
                        if (favored != it.favored) {
                            it.favored = favored
                            postsInteractor.updatePost(PostDomainModel(userId, it.id, it.title, it.body, favored))
                        }
                    }
                }
            }
            allPosts.refresh()
        }
    }

    private fun filterPosts(): List<PostPresentationModel> {
        val allPosts = allPosts.value
        val onlyFavored = showOnlyFavoredPosts.value ?: false

        if (allPosts == null) {
            return emptyList()
        }

        return if (onlyFavored) {
            allPosts.filter { post ->
                post.favored
            }
        } else {
            allPosts
        }
    }
}