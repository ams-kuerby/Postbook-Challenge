package de.adessomobile.postbookchallenge.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import de.adessomobile.postbookchallenge.data.client.PostsApi
import de.adessomobile.postbookchallenge.data.client.models.CommentDto
import de.adessomobile.postbookchallenge.data.client.models.PostDto
import de.adessomobile.postbookchallenge.data.persistence.FavoredPostPersistence
import de.adessomobile.postbookchallenge.repository.models.CommentDomainModel
import de.adessomobile.postbookchallenge.repository.models.PostDomainModel
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldEqual
import org.junit.Test

class PostsRepositoryImplTest {

    private val postsApi: PostsApi = mock()
    private val favoredPostPersistence: FavoredPostPersistence = mock()

    private val underTest = PostsRepositoryImpl(postsApi, favoredPostPersistence)

    @Test
    fun `Given an empty list, when listPosts, then an empty list should be returned`() = runBlocking<Unit> {
        val userId = 2
        whenever(postsApi.listPosts(userId)).thenReturn(async { emptyList<PostDto>() })
        whenever(favoredPostPersistence.getFavoredPostIds()).thenReturn(emptySet())

        val actual = underTest.listPosts(userId)

        actual.shouldBeEmpty()
    }

    @Test
    fun `Given one unfavored post, when listPosts, then one unfavored post should be converted and returned`() =
        runBlocking<Unit> {
            val userId = 2
            val postDtos = listOf(
                PostDto(2, 3, "title 3", "body 3")
            )
            whenever(postsApi.listPosts(userId)).thenReturn(async { postDtos })
            whenever(favoredPostPersistence.getFavoredPostIds()).thenReturn(setOf(2, 4))

            val actual = underTest.listPosts(userId)

            val expected = listOf(
                PostDomainModel(2, 3, "title 3", "body 3", false)
            )
            actual shouldEqual expected
        }

    @Test
    fun `Given one favored posts, when listPosts, then one favored posts should be converted and returned`() =
        runBlocking<Unit> {
            val userId = 2
            val postDtos = listOf(
                PostDto(2, 4, "title 4", "body 4")
            )
            whenever(postsApi.listPosts(userId)).thenReturn(async { postDtos })
            whenever(favoredPostPersistence.getFavoredPostIds()).thenReturn(setOf(2, 4))

            val actual = underTest.listPosts(userId)

            val expected = listOf(
                PostDomainModel(2, 4, "title 4", "body 4", true)
            )
            actual shouldEqual expected
        }

    @Test
    fun `Given a list of posts, when listPosts, then these posts should be converted and returned`() =
        runBlocking<Unit> {
            val userId = 2
            val postDtos = listOf(
                PostDto(2, 3, "title 3", "body 3"),
                PostDto(2, 4, "title 4", "body 4"),
                PostDto(2, 5, "title 5", "body 5")
            )
            whenever(postsApi.listPosts(userId)).thenReturn(async { postDtos })
            whenever(favoredPostPersistence.getFavoredPostIds()).thenReturn(setOf(2, 4))

            val actual = underTest.listPosts(userId)

            val expected = listOf(
                PostDomainModel(2, 3, "title 3", "body 3", false),
                PostDomainModel(2, 4, "title 4", "body 4", true),
                PostDomainModel(2, 5, "title 5", "body 5", false)
            )
            actual shouldEqual expected
        }

    @Test
    fun `Given a favored post, when updatePost, then post should be favored`() = runBlocking {
        val postId = 2
        val post = PostDomainModel(1, postId, "post title", "post body", true)

        underTest.updatePost(post)

        verify(favoredPostPersistence).favor(postId)
        verifyNoMoreInteractions(favoredPostPersistence)
    }

    @Test
    fun `Given an unfavored post, when updatePost, then post should be favored`() = runBlocking {
        val postId = 3
        val post = PostDomainModel(1, postId, "post title", "post body", false)

        underTest.updatePost(post)

        verify(favoredPostPersistence).unfavor(postId)
        verifyNoMoreInteractions(favoredPostPersistence)
    }

    @Test
    fun `Given an empty list, when listComments, then an empty list should be returned`() = runBlocking<Unit> {
        val postId = 2
        whenever(postsApi.listComments(postId)).thenReturn(async { emptyList<CommentDto>() })

        val actual = underTest.listComments(postId)

        actual.shouldBeEmpty()
    }

    @Test
    fun `Given one favored comments, when listComments, then one favored comments should be converted and returned`() =
        runBlocking<Unit> {
            val userId = 2
            val postDtos = listOf(
                CommentDto(2, 4, "name 4", "email 4", "body 4")
            )
            whenever(postsApi.listComments(userId)).thenReturn(async { postDtos })

            val actual = underTest.listComments(userId)

            val expected = listOf(
                CommentDomainModel(2, 4, "name 4", "email 4", "body 4")
            )
            actual shouldEqual expected
        }

    @Test
    fun `Given a list of comments, when listComments, then these comments should be converted and returned`() =
        runBlocking<Unit> {
            val userId = 2
            val postDtos = listOf(
                CommentDto(2, 3, "name 3", "email 3", "body 3"),
                CommentDto(2, 4, "name 4", "email 4", "body 4"),
                CommentDto(2, 5, "name 5", "email 5", "body 5")
            )
            whenever(postsApi.listComments(userId)).thenReturn(async { postDtos })

            val actual = underTest.listComments(userId)

            val expected = listOf(
                CommentDomainModel(2, 3, "name 3", "email 3", "body 3"),
                CommentDomainModel(2, 4, "name 4", "email 4", "body 4"),
                CommentDomainModel(2, 5, "name 5", "email 5", "body 5")
            )
            actual shouldEqual expected
        }
}