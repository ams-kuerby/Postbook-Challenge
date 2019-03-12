package de.adessomobile.postbookchallenge.ui.posts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.adessomobile.postbookchallenge.R

/**
 * Activity to show the posts for a given user.
 */
class PostsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)
    }
}
