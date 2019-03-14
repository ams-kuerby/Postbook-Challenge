package de.adessomobile.postbookchallenge.data.client

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import de.adessomobile.postbookchallenge.data.client.models.CommentDto
import de.adessomobile.postbookchallenge.data.client.models.PostDto
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostsApiTest {

    @get:Rule
    val server = MockWebServer()

    private lateinit var underTest: PostsApi

    @Before
    fun setUp() {
        val gsonConverterFactory = GsonConverterFactory.create()

        val retrofit = Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .baseUrl(server.url("/"))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(gsonConverterFactory)
            .build()

        underTest = retrofit.create(PostsApi::class.java)
    }

    @Test
    fun `Given userId 1, when listPosts, then the endpoint posts with userId 1 is called`() {
        server.enqueue(MockResponse().setBody(bodyPosts))

        val userId = 1
        runBlocking { underTest.listPosts(userId).await() }

        val request = server.takeRequest()

        request.path shouldEqual "/posts?userId=$userId"
    }

    @Test
    fun `Given a post json, when listPosts, then correct dtos are returned`() {
        server.enqueue(MockResponse().setBody(bodyPosts))

        val posts = runBlocking { underTest.listPosts(1).await() }

        val expected = listOf(
            PostDto(1, 1, "post title 1", "post 1"),
            PostDto(1, 2, "post title 2", "post 2")
        )
        posts shouldEqual expected
    }

    @Test
    fun `Given postId 2, when getPost, then the endpoint posts with postId 2 is called`() {
        server.enqueue(MockResponse().setBody(bodySinglePost))

        val postId = 2
        runBlocking { underTest.getPost(postId).await() }

        val request = server.takeRequest()

        request.path shouldEqual "/posts/$postId"
    }

    @Test
    fun `Given a post json, when getPost, then correct dto is returned`() {
        server.enqueue(MockResponse().setBody(bodySinglePost))

        val posts = runBlocking { underTest.getPost(2).await() }

        val expected = PostDto(1, 2, "post title 2", "post 2")
        posts shouldEqual expected
    }

    @Test
    fun `Given postId 1, when listComments, then the endpoint comments with postId 1 is called`() {
        server.enqueue(MockResponse().setBody(bodyComments))

        val postId = 1
        runBlocking { underTest.listComments(postId).await() }

        val request = server.takeRequest()

        request.path shouldEqual "/comments?postId=$postId"
    }

    @Test
    fun `Given a comment json, when listComments, then correct dtos are returned`() {
        server.enqueue(MockResponse().setBody(bodyComments))

        val comments = runBlocking { underTest.listComments(1).await() }

        val expected = listOf(
            CommentDto(1, 1, "comment name 1", "comment1@example.com", "comment body 1"),
            CommentDto(
                1, 2, "comment name 2", "comment2@example.org", "comment body 2"
            )
        )
        comments shouldEqual expected
    }
}

private val bodyPosts = """[
  {
    "userId": 1,
    "id": 1,
    "title": "post title 1",
    "body": "post 1"
  },
  {
    "userId": 1,
    "id": 2,
    "title": "post title 2",
    "body": "post 2"
  }
]
"""

private val bodySinglePost = """{
  "userId": 1,
  "id": 2,
  "title": "post title 2",
  "body": "post 2"
}"""

private val bodyComments = """[
  {
    "postId": 1,
    "id": 1,
    "name": "comment name 1",
    "email": "comment1@example.com",
    "body": "comment body 1"
  },
  {
    "postId": 1,
    "id": 2,
    "name": "comment name 2",
    "email": "comment2@example.org",
    "body": "comment body 2"
  }
]"""