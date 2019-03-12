package de.adessomobile.postbookchallenge.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import de.adessomobile.postbookchallenge.R
import de.adessomobile.postbookchallenge.databinding.ActivityLoginBinding
import de.adessomobile.postbookchallenge.ui.posts.PostsActivity
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Activity to login with a userId.
 */
class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
            .let {
                it.viewModel = viewModel
                it.lifecycleOwner = this
            }

        viewModel.events.observe(this, Observer(this::handleEvents))
    }

    private fun handleEvents(loginEvent: LoginEvent) {
        when (loginEvent) {
            is LoginEvent.ShowUserPosts ->
                startActivity(PostsActivity.createIntent(this, loginEvent.userId))
        }
    }
}
