package de.adessomobile.postbookchallenge.ui.posts

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import de.adessomobile.postbookchallenge.R
import de.adessomobile.postbookchallenge.databinding.ActivityPostsBinding
import de.adessomobile.postbookchallenge.ui.postcomments.PostCommentsActivity
import kotlinx.android.synthetic.main.activity_posts.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Activity to show the posts for a given user.
 */
class PostsActivity : AppCompatActivity() {

    private val viewModel: PostsViewModel by viewModel()

    private lateinit var postsAdapter: PostsRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityPostsBinding>(this, R.layout.activity_posts)
            .let {
                it.viewModel = viewModel
                it.lifecycleOwner = this
            }
        setSupportActionBar(posts_tb_toolbar)

        initRecyclerView()

        viewModel.events.observe(this, Observer(this::handleEvents))

        val userId = intent.extras?.getInt(EXTRA_USER_ID) ?: 0
        viewModel.loadPosts(userId)
    }

    private fun initRecyclerView() {
        postsAdapter = PostsRecyclerAdapter(viewModel::onPostClick, viewModel::onPostFavoriteClick)
        posts_rv_posts.adapter = postsAdapter

        viewModel.posts.observe(this, Observer {
            postsAdapter.submitList(it)
        })
    }

    private fun handleEvents(postsEvent: PostsEvent) {
        when (postsEvent) {
            is PostsEvent.ShowPostComments ->
                startActivity(PostCommentsActivity.createIntent(this, postsEvent.postId))
        }
    }

    companion object {
        private const val EXTRA_USER_ID = "extra_posts_user_id"

        /**
         * Return an Intent to start this Activity with the given [userId].
         */
        fun createIntent(context: Context, userId: Int): Intent {
            return Intent(context, PostsActivity::class.java)
                .apply {
                    putExtra(EXTRA_USER_ID, userId)
                }
        }
    }
}
