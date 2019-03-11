package de.adessomobile.postbookchallenge.repository

import de.adessomobile.postbookchallenge.data.client.PostsApi
import de.adessomobile.postbookchallenge.data.persistence.FavoredPostPersistence
import de.adessomobile.postbookchallenge.repository.models.CommentDomainModel
import de.adessomobile.postbookchallenge.repository.models.PostDomainModel

/**
 * Implementation of [PostsRepository]
 */
class PostsRepositoryImpl(
    private val postsApi: PostsApi,
    private val favoredPostPersistence: FavoredPostPersistence
) : PostsRepository {

    override suspend fun listPosts(userId: Int): List<PostDomainModel> {
        val favoredPostIds = favoredPostPersistence.getFavoredPostIds()
        return postsApi.listPosts(userId)
            .await()
            .map {
                val favored = favoredPostIds.contains(it.id)
                PostDomainModel(it.userId, it.id, it.title, it.body, favored)
            }
    }

    override suspend fun updatePost(post: PostDomainModel) {
        if (post.favored) {
            favoredPostPersistence.favor(post.id)
        } else {
            favoredPostPersistence.unfavor(post.id)
        }
    }

    override suspend fun listComments(postId: Int): List<CommentDomainModel> {
        return postsApi.listComments(postId)
            .await()
            .map {
                CommentDomainModel(it.postId, it.id, it.name, it.email, it.body)
            }
    }
}