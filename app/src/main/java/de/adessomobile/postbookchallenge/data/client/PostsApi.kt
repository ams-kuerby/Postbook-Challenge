package de.adessomobile.postbookchallenge.data.client

import de.adessomobile.postbookchallenge.data.client.models.CommentDto
import de.adessomobile.postbookchallenge.data.client.models.PostDto
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * RetrofitInterface for the posts API of https://jsonplaceholder.typicode.com
 */
interface PostsApi {

    /**
     * Get a list of all posts of the user with the given [userId].
     */
    @GET("posts")
    fun listPosts(@Query("userId") userId: Int): Deferred<List<PostDto>>

    /**
     * Get the post with the given [postId]
     */
    @GET("posts/{postId}")
    fun getPost(@Path("postId") postId: Int): Deferred<PostDto>

    /**
     * Get a list of all comments for a post with the given [postId].
     */
    @GET("comments")
    fun listComments(@Query("postId") postId: Int): Deferred<List<CommentDto>>
}