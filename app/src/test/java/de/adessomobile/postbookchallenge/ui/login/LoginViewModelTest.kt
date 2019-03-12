package de.adessomobile.postbookchallenge.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import de.adessomobile.postbookchallenge.base.coroutines.MockCoroutineContextProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.amshove.kluent.any
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldEqual
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val underTest = LoginViewModel(MockCoroutineContextProvider())

    @Test
    fun `Given a userId, when loginClicked, then ShowUserPosts event should be emitted`() {
        val observer: (LoginEvent) -> Unit = mock {
            onGeneric { invoke(any()) } doReturn Unit
        }
        underTest.events.observeForever(Observer(observer))

        val userId = 1234
        underTest.userId = userId.toString()

        underTest.loginClicked()

        val expected = LoginEvent.ShowUserPosts(userId)
        verify(observer).invoke(expected)
        underTest.events.value shouldEqual expected
    }

    @Test
    fun `Given a blank userId, when loginClicked, then ShowUserPosts event should not change`() {
        val observer: (LoginEvent) -> Unit = mock {
            onGeneric { invoke(any()) } doReturn Unit
        }
        underTest.events.observeForever(Observer(observer))

        underTest.userId = " "
        val expected = underTest.events.value

        underTest.loginClicked()

        verifyZeroInteractions(observer)
        underTest.events.value shouldBe expected
    }
}