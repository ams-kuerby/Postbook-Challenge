package de.adessomobile.postbookchallenge.ui.postcomments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import de.adessomobile.postbookchallenge.R
import kotlinx.android.synthetic.main.activity_post_comments.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Activity to show the comments for a given post.
 */
class PostCommentsActivity : AppCompatActivity() {

    private val viewModel: PostCommentsViewModel by viewModel()

    private lateinit var postCommentsAdapter: PostCommentsRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_comments)
        setSupportActionBar(postComments_tb_toolbar)

        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()

        val postId = intent.extras?.getInt(PostCommentsActivity.EXTRA_POSTS_ID) ?: 0
        viewModel.loadData(postId)
    }

    private fun initRecyclerView() {
        postCommentsAdapter = PostCommentsRecyclerAdapter(viewModel::onPostFavoriteClick)
        postComments_rv_comments.adapter = postCommentsAdapter

        viewModel.items.observe(this, Observer {
            postCommentsAdapter.submitList(it)
        })
    }

    companion object {
        private const val EXTRA_POSTS_ID = "extra_post_comments_post_id"

        /**
         * Return an Intent to start this Activity with the given [postId].
         */
        fun createIntent(context: Context, postId: Int): Intent {
            return Intent(context, PostCommentsActivity::class.java)
                .apply {
                    putExtra(EXTRA_POSTS_ID, postId)
                }
        }
    }
}
