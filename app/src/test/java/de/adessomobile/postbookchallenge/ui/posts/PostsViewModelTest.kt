package de.adessomobile.postbookchallenge.ui.posts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import de.adessomobile.postbookchallenge.base.coroutines.MockCoroutineContextProvider
import de.adessomobile.postbookchallenge.repository.models.PostDomainModel
import de.adessomobile.postbookchallenge.ui.models.PostPresentationModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.any
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
    fun testLoadPosts() {
        val observer: (List<PostPresentationModel>) -> Unit = mock {
            onGeneric { invoke(any()) } doReturn Unit
        }
        underTest.posts.observeForever(Observer(observer))

        val userId = 330
        val postDomainModels = listOf(
            PostDomainModel(userId, 3, "title 3", "body 3", false),
            PostDomainModel(userId, 4, "title 4", "body 4", true),
            PostDomainModel(userId, 5, "title 5", "body 5", false)
        )
        runBlocking { whenever(postsInteractorMock.getPosts(userId)).thenReturn(postDomainModels) }

        underTest.loadPosts(userId)

        val expected = listOf(
            PostPresentationModel(3, "title 3", "body 3", false),
            PostPresentationModel(4, "title 4", "body 4", true),
            PostPresentationModel(5, "title 5", "body 5", false)
        )
        verify(observer).invoke(expected)
    }
}