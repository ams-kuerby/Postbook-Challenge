package de.adessomobile.postbookchallenge.ui.posts

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import de.adessomobile.postbookchallenge.repository.PostsRepository
import de.adessomobile.postbookchallenge.repository.models.PostDomainModel
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.Test

class PostsInteractorImplTest {

    private val postsRepositoryMock: PostsRepository = mock()

    private val underTest = PostsInteractorImpl(postsRepositoryMock)

    @Test
    fun `Given a list of models, when getPosts, the list should be returned`() = runBlocking<Unit> {
        val userId = 2
        val expected = listOf(
            PostDomainModel(userId, 3, "title 3", "body 3", false),
            PostDomainModel(userId, 4, "title 4", "body 4", true),
            PostDomainModel(userId, 5, "title 5", "body 5", false)
        )
        whenever(postsRepositoryMock.listPosts(userId)).thenReturn(expected)

        val actual = underTest.getPosts(userId)

        actual shouldEqual expected
    }
}