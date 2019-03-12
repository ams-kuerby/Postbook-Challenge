package de.adessomobile.postbookchallenge.ui.posts

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import de.adessomobile.postbookchallenge.base.BaseViewModel
import de.adessomobile.postbookchallenge.base.coroutines.CoroutineContextProvider
import de.adessomobile.postbookchallenge.ui.models.PostPresentationModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Architecture Component ViewModel for the PostsActivity
 */
class PostsViewModel(
    private val postsInteractor: PostsInteractor,
    coroutineContextProvider: CoroutineContextProvider
) : BaseViewModel(coroutineContextProvider) {

    private val allPosts = MutableLiveData<List<PostPresentationModel>>()

    private val showOnlyFavoredPosts = MutableLiveData<Boolean>()

    val posts = MediatorLiveData<List<PostPresentationModel>>()
        .apply {
            addSource(allPosts) {
                value = filterPosts()
            }
            addSource(showOnlyFavoredPosts) {
                value = filterPosts()
            }
        }

    fun loadPosts(userId: Int) {
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