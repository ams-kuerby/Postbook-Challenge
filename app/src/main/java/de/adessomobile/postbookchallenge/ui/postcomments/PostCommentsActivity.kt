package de.adessomobile.postbookchallenge.ui.postcomments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.adessomobile.postbookchallenge.R
import kotlinx.android.synthetic.main.activity_post_comments.*

class PostCommentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_comments)
        setSupportActionBar(toolbar)
    }

    companion object {
        private const val EXTRA_POSTS_ID = "extra_post_comments_post_id"

        /**
         * Return an Intent to start this Activity with the given [userId].
         */
        fun createIntent(context: Context, userId: Int): Intent {
            return Intent(context, PostCommentsActivity::class.java)
                .apply {
                    putExtra(EXTRA_POSTS_ID, userId)
                }
        }
    }
}
