package de.adessomobile.postbookchallenge.ui.posts

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.adessomobile.postbookchallenge.R
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Activity to show the posts for a given user.
 */
class PostsActivity : AppCompatActivity() {

    private val viewModel: PostsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        val userId = intent.extras?.getInt(EXTRA_USER_ID) ?: 0
        viewModel.loadPosts(userId)
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
