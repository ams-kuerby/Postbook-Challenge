package de.adessomobile.postbookchallenge.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import de.adessomobile.postbookchallenge.R
import de.adessomobile.postbookchallenge.databinding.ActivityLoginBinding
import org.koin.android.ext.android.inject

/**
 * Activity to login with a userId.
 */
class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
            .let {
                it.viewModel = viewModel
                it.lifecycleOwner = this
            }
    }
}
