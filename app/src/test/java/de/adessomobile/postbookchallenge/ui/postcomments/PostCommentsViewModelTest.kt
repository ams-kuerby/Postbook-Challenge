package de.adessomobile.postbookchallenge.ui.postcomments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import de.adessomobile.postbookchallenge.base.coroutines.MockCoroutineContextProvider
import de.adessomobile.postbookchallenge.repository.models.CommentDomainModel
import de.adessomobile.postbookchallenge.repository.models.PostDomainModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PostCommentsViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val postCommentsInteractorMock: PostCommentsInteractor = mock()

    private val underTest = PostCommentsViewModel(postCommentsInteractorMock, MockCoroutineContextProvider())

    @Test
    fun `Given a postId, when loadData, then items should be updated`() {
        // GIVEN
        val observerMock: (List<PostCommentsPresentationModel>) -> Unit = mock {
            onGeneric { invoke(any()) } doReturn Unit
        }
        underTest.items.observeForever(Observer(observerMock))

        val userId = 330
        val postId = 3
        val postDomainModel = PostDomainModel(userId, postId, "post title 3", "post body 3", false)
        val commentDomainModels = listOf(
            CommentDomainModel(postId, 4, "name 4", "email 4", "body 4"),
            CommentDomainModel(postId, 5, "name 5", "email 5", "body 5"),
            CommentDomainModel(postId, 6, "name 6", "email 6", "body 6")
        )
        runBlocking {
            whenever(postCommentsInteractorMock.getPost(postId)).thenReturn(postDomainModel)
            whenever(postCommentsInteractorMock.getComments(postId)).thenReturn(commentDomainModels)
        }

        // WHEN
        underTest.loadData(postId)

        // THEN
        val expected = listOf(
            PostCommentsPresentationModel.PostPresentationModel(userId, postId, "post title 3", "post body 3", false),
            PostCommentsPresentationModel.CommentPresentationModel(4, "name 4", "email 4", "body 4"),
            PostCommentsPresentationModel.CommentPresentationModel(5, "name 5", "email 5", "body 5"),
            PostCommentsPresentationModel.CommentPresentationModel(6, "name 6", "email 6", "body 6")
        )
        verify(observerMock).invoke(expected)
        underTest.items.value shouldEqual expected
    }


    @Test
    fun `Given items, when onPostFavoriteClick with changing favored, then update in Interactor`() {
        // GIVEN
        val userId = 330
        val postId = 3
        val postDomainModel = PostDomainModel(userId, postId, "post title 3", "post body 3", false)
        val commentDomainModels = listOf(
            CommentDomainModel(postId, 4, "name 4", "email 4", "body 4"),
            CommentDomainModel(postId, 5, "name 5", "email 5", "body 5"),
            CommentDomainModel(postId, 6, "name 6", "email 6", "body 6")
        )
        runBlocking {
            whenever(postCommentsInteractorMock.getPost(postId)).thenReturn(postDomainModel)
            whenever(postCommentsInteractorMock.getComments(postId)).thenReturn(commentDomainModels)
        }
        underTest.loadData(postId)

        // WHEN
        underTest.onPostFavoriteClick(true)

        // THEN
        val expected = PostDomainModel(userId, 3, "post title 3", "post body 3", true)
        runBlocking { verify(postCommentsInteractorMock).updatePost(expected) }
    }

    @Test
    fun `Given a postId and posts, when onPostFavoriteClick with changing favored, then update LiveData`() {
        // GIVEN

        val userId = 330
        val postId = 3
        val postDomainModel = PostDomainModel(userId, postId, "post title 3", "post body 3", false)
        val commentDomainModels = listOf(
            CommentDomainModel(postId, 4, "name 4", "email 4", "body 4"),
            CommentDomainModel(postId, 5, "name 5", "email 5", "body 5"),
            CommentDomainModel(postId, 6, "name 6", "email 6", "body 6")
        )
        runBlocking {
            whenever(postCommentsInteractorMock.getPost(postId)).thenReturn(postDomainModel)
            whenever(postCommentsInteractorMock.getComments(postId)).thenReturn(commentDomainModels)
        }
        underTest.loadData(postId)

        val observerMock: (List<PostCommentsPresentationModel>) -> Unit = mock {
            onGeneric { invoke(any()) } doReturn Unit
        }
        underTest.items.observeForever(Observer(observerMock))

        // WHEN
        underTest.onPostFavoriteClick(true)

        val expected = listOf(
            PostCommentsPresentationModel.PostPresentationModel(userId, postId, "post title 3", "post body 3", true),
            PostCommentsPresentationModel.CommentPresentationModel(4, "name 4", "email 4", "body 4"),
            PostCommentsPresentationModel.CommentPresentationModel(5, "name 5", "email 5", "body 5"),
            PostCommentsPresentationModel.CommentPresentationModel(6, "name 6", "email 6", "body 6")
        )
        verify(observerMock, atLeastOnce()).invoke(expected)
    }

    @Test
    fun `Given a postId and posts, when onPostFavoriteClick without changing favored, then don't update in Interactor`() {
        // GIVEN
        val userId = 330
        val postId = 3
        val postDomainModel = PostDomainModel(userId, postId, "post title 3", "post body 3", false)
        val commentDomainModels = listOf(
            CommentDomainModel(postId, 4, "name 4", "email 4", "body 4"),
            CommentDomainModel(postId, 5, "name 5", "email 5", "body 5"),
            CommentDomainModel(postId, 6, "name 6", "email 6", "body 6")
        )
        runBlocking {
            whenever(postCommentsInteractorMock.getPost(postId)).thenReturn(postDomainModel)
            whenever(postCommentsInteractorMock.getComments(postId)).thenReturn(commentDomainModels)
        }
        underTest.loadData(postId)

        // WHEN
        underTest.onPostFavoriteClick(false)

        // THEN
        runBlocking { verify(postCommentsInteractorMock, never()).updatePost(any()) }
    }

    @Test
    fun `Given a postId and posts, when onPostFavoriteClick without changing favored, then update LiveData`() {
        // GIVEN
        val observerMock: (List<PostCommentsPresentationModel>) -> Unit = mock {
            onGeneric { invoke(any()) } doReturn Unit
        }
        underTest.items.observeForever(Observer(observerMock))

        val userId = 330
        val postId = 3
        val postDomainModel = PostDomainModel(userId, postId, "post title 3", "post body 3", false)
        val commentDomainModels = listOf(
            CommentDomainModel(postId, 4, "name 4", "email 4", "body 4"),
            CommentDomainModel(postId, 5, "name 5", "email 5", "body 5"),
            CommentDomainModel(postId, 6, "name 6", "email 6", "body 6")
        )
        runBlocking {
            whenever(postCommentsInteractorMock.getPost(postId)).thenReturn(postDomainModel)
            whenever(postCommentsInteractorMock.getComments(postId)).thenReturn(commentDomainModels)
        }
        underTest.loadData(postId)

        // WHEN
        underTest.onPostFavoriteClick(false)

        val expected = listOf(
            PostCommentsPresentationModel.PostPresentationModel(userId, postId, "post title 3", "post body 3", false),
            PostCommentsPresentationModel.CommentPresentationModel(4, "name 4", "email 4", "body 4"),
            PostCommentsPresentationModel.CommentPresentationModel(5, "name 5", "email 5", "body 5"),
            PostCommentsPresentationModel.CommentPresentationModel(6, "name 6", "email 6", "body 6")
        )
        verify(observerMock, atLeastOnce()).invoke(expected)
    }
}