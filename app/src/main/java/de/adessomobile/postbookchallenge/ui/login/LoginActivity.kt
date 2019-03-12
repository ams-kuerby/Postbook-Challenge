package de.adessomobile.postbookchallenge.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.adessomobile.postbookchallenge.R
import org.koin.android.ext.android.inject

/**
 * Activity to login with a userId.
 */
class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}
