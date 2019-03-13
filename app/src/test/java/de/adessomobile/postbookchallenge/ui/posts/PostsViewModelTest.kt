package de.adessomobile.postbookchallenge.ui.posts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import de.adessomobile.postbookchallenge.base.coroutines.MockCoroutineContextProvider
import de.adessomobile.postbookchallenge.repository.models.PostDomainModel
import de.adessomobile.postbookchallenge.ui.models.PostPresentationModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PostsViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val postsInteractorMock: PostsInteractor = mock()

    private val underTest = PostsViewModel(postsInteractorMock, MockCoroutineContextProvider())

    @Test
    fun `Given a userId, when loadPosts, then posts should be updated`() {
        // GIVEN
        val observerMock: (List<PostPresentationModel>) -> Unit = mock {
            onGeneric { invoke(any()) } doReturn Unit
        }
        underTest.posts.observeForever(Observer(observerMock))

        val userId = 330
        val postDomainModels = listOf(
            PostDomainModel(userId, 3, "title 3", "body 3", false),
            PostDomainModel(userId, 4, "title 4", "body 4", true),
            PostDomainModel(userId, 5, "title 5", "body 5", false)
        )
        runBlocking { whenever(postsInteractorMock.getPosts(userId)).thenReturn(postDomainModels) }

        // WHEN
        underTest.loadPosts(userId)

        // THEN
        val expected = listOf(
            PostPresentationModel(3, "title 3", "body 3", false),
            PostPresentationModel(4, "title 4", "body 4", true),
            PostPresentationModel(5, "title 5", "body 5", false)
        )
        verify(observerMock).invoke(expected)
        underTest.posts.value shouldEqual expected
    }

    @Test
    fun `Given posts, when showOnlyFavoredPosts is true, posts should be filtered`() {
        // GIVEN
        val userId = 330
        val postDomainModels = listOf(
            PostDomainModel(userId, 3, "title 3", "body 3", false),
            PostDomainModel(userId, 4, "title 4", "body 4", true),
            PostDomainModel(userId, 5, "title 5", "body 5", false)
        )
        runBlocking { whenever(postsInteractorMock.getPosts(userId)).thenReturn(postDomainModels) }
        underTest.loadPosts(userId)

        val observerMock: (List<PostPresentationModel>) -> Unit = mock {
            onGeneric { invoke(any()) } doReturn Unit
        }
        underTest.posts.observeForever(Observer(observerMock))

        // WHEN
        underTest.showOnlyFavoredPosts.value = true

        // THEN
        val expected = listOf(
            PostPresentationModel(4, "title 4", "body 4", true)
        )
        verify(observerMock).invoke(expected)
        underTest.posts.value shouldEqual expected
    }

    @Test
    fun `Given posts, when showOnlyFavoredPosts is false, posts should not be filtered`() {
        // GIVEN
        underTest.showOnlyFavoredPosts.value = true

        val userId = 330
        val postDomainModels = listOf(
            PostDomainModel(userId, 3, "title 3", "body 3", false),
            PostDomainModel(userId, 4, "title 4", "body 4", true),
            PostDomainModel(userId, 5, "title 5", "body 5", false)
        )
        runBlocking { whenever(postsInteractorMock.getPosts(userId)).thenReturn(postDomainModels) }
        underTest.loadPosts(userId)

        val observerMock: (List<PostPresentationModel>) -> Unit = mock {
            onGeneric { invoke(any()) } doReturn Unit
        }
        underTest.posts.observeForever(Observer(observerMock))

        // WHEN
        underTest.showOnlyFavoredPosts.value = false

        // THEN
        val expected = listOf(
            PostPresentationModel(3, "title 3", "body 3", false),
            PostPresentationModel(4, "title 4", "body 4", true),
            PostPresentationModel(5, "title 5", "body 5", false)
        )
        verify(observerMock).invoke(expected)
        underTest.posts.value shouldEqual expected
    }

    @Test
    fun `Given a postId, when onPostClick, then emit ShowPostComments`() {
        // GIVEN
        val postId = 753

        val observerMock: (PostsEvent) -> Unit = mock {
            onGeneric { invoke(any()) } doReturn Unit
        }
        underTest.events.observeForever(Observer(observerMock))

        // WHEN
        underTest.onPostClick(postId)

        // THEN
        verify(observerMock).invoke(PostsEvent.ShowPostComments(postId))
        verifyNoMoreInteractions(observerMock)
    }

    @Test
    fun `Given a postId and posts, when onPostFavoriteClick with changing favored, then update in Interactor`() {
        // GIVEN
        val userId = 330
        val postDomainModels = listOf(
            PostDomainModel(userId, 3, "title 3", "body 3", false),
            PostDomainModel(userId, 4, "title 4", "body 4", true),
            PostDomainModel(userId, 5, "title 5", "body 5", false)
        )
        runBlocking { whenever(postsInteractorMock.getPosts(userId)).thenReturn(postDomainModels) }
        underTest.loadPosts(userId)

        // WHEN
        underTest.onPostFavoriteClick(3, true)

        // THEN
        val expected = PostDomainModel(userId, 3, "title 3", "body 3", true)
        runBlocking { verify(postsInteractorMock).updatePost(expected) }
    }

    @Test
    fun `Given a postId and posts, when onPostFavoriteClick with changing favored, then update LiveData`() {
        // GIVEN
        val userId = 330
        val postDomainModels = listOf(
            PostDomainModel(userId, 3, "title 3", "body 3", false),
            PostDomainModel(userId, 4, "title 4", "body 4", true),
            PostDomainModel(userId, 5, "title 5", "body 5", false)
        )
        runBlocking { whenever(postsInteractorMock.getPosts(userId)).thenReturn(postDomainModels) }
        underTest.loadPosts(userId)

        // WHEN
        underTest.onPostFavoriteClick(3, true)

        // THEN
        val observerMock: (List<PostPresentationModel>) -> Unit = mock {
            onGeneric { invoke(any()) } doAnswer {
                println("invoke: ${it.arguments.first()}")
            }
        }
        val observer = Observer(observerMock)
        underTest.posts.observeForever(observer)

        val expected = listOf(
            PostPresentationModel(3, "title 3", "body 3", true),
            PostPresentationModel(4, "title 4", "body 4", true),
            PostPresentationModel(5, "title 5", "body 5", false)
        )
        verify(observerMock).invoke(expected)
    }

    @Test
    fun `Given a postId and posts, when onPostFavoriteClick without changing favored, then don't update in Interactor`() {
        // GIVEN
        val userId = 330
        val postDomainModels = listOf(
            PostDomainModel(userId, 3, "title 3", "body 3", false),
            PostDomainModel(userId, 4, "title 4", "body 4", true),
            PostDomainModel(userId, 5, "title 5", "body 5", false)
        )
        runBlocking { whenever(postsInteractorMock.getPosts(userId)).thenReturn(postDomainModels) }
        underTest.loadPosts(userId)

        // WHEN
        underTest.onPostFavoriteClick(4, true)

        // THEN
        runBlocking { verify(postsInteractorMock, never()).updatePost(any()) }
    }

    @Test
    fun `Given a postId and posts, when onPostFavoriteClick without changing favored, then update LiveData`() {
        // GIVEN
        val userId = 330
        val postDomainModels = listOf(
            PostDomainModel(userId, 3, "title 3", "body 3", false),
            PostDomainModel(userId, 4, "title 4", "body 4", true),
            PostDomainModel(userId, 5, "title 5", "body 5", false)
        )
        runBlocking { whenever(postsInteractorMock.getPosts(userId)).thenReturn(postDomainModels) }
        underTest.loadPosts(userId)

        // WHEN
        underTest.onPostFavoriteClick(4, true)

        // THEN
        val observerMock: (List<PostPresentationModel>) -> Unit = mock {
            onGeneric { invoke(any()) } doAnswer {
                println("invoke: ${it.arguments.first()}")
            }
        }
        underTest.posts.observeForever(Observer(observerMock))

        val expected = listOf(
            PostPresentationModel(3, "title 3", "body 3", false),
            PostPresentationModel(4, "title 4", "body 4", true),
            PostPresentationModel(5, "title 5", "body 5", false)
        )
        verify(observerMock).invoke(expected)
    }
}