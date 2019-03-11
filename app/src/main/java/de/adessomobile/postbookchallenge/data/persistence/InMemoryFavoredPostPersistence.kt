package de.adessomobile.postbookchallenge.data.persistence

class InMemoryFavoredPostPersistence : FavoredPostPersistence {

    private val favoredPostIds = mutableSetOf<Int>()

    override suspend fun getFavoredPostIds(): Set<Int> = favoredPostIds

    override suspend fun favor(postId: Int) {
        favoredPostIds.add(postId)
    }

    override suspend fun unfavor(postId: Int) {
        favoredPostIds.remove(postId)
    }
}