package de.adessomobile.postbookchallenge.ui.postcomments

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import de.adessomobile.postbookchallenge.repository.PostsRepository
import de.adessomobile.postbookchallenge.repository.models.CommentDomainModel
import de.adessomobile.postbookchallenge.repository.models.PostDomainModel
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.Test

class PostCommentsInteractorImplTest {

    private val postsRepositoryMock: PostsRepository = mock()

    private val underTest = PostCommentsInteractorImpl(postsRepositoryMock)

    @Test
    fun `Given a list of models, when getPosts, the list should be returned`() = runBlocking<Unit> {
        val postId = 3
        val expected = PostDomainModel(2, postId, "title 3", "body 3", false)
        whenever(postsRepositoryMock.getPost(postId)).thenReturn(expected)

        val actual = underTest.getPost(postId)

        actual shouldEqual expected
    }

    @Test
    fun `Given a post, when updatePost, the post should be updated in the repository`() = runBlocking {
        val post = PostDomainModel(2, 3, "title 3", "body 3", false)

        underTest.updatePost(post)

        verify(postsRepositoryMock).updatePost(post)
    }

    @Test
    fun `Given a list of comments, when getPosts, the list should be returned`() = runBlocking<Unit> {
        val postId = 2
        val expected = listOf(
            CommentDomainModel(postId, 3, "name 3", "email 3", "body 3"),
            CommentDomainModel(postId, 4, "name 4", "email 4", "body 4"),
            CommentDomainModel(postId, 5, "name 5", "email 5", "body 5")
        )
        whenever(postsRepositoryMock.listComments(postId)).thenReturn(expected)

        val actual = underTest.getComments(postId)

        actual shouldEqual expected
    }
}