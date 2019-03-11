package de.adessomobile.postbookchallenge.data

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import de.adessomobile.postbookchallenge.R
import de.adessomobile.postbookchallenge.data.client.PostsApi
import de.adessomobile.postbookchallenge.data.persistence.FavoredPostPersistence
import de.adessomobile.postbookchallenge.data.persistence.InMemoryFavoredPostPersistence
import okhttp3.OkHttpClient
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataKoinModule = module {

    single { get<Retrofit>().create(PostsApi::class.java) }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(get<Context>().getString(R.string.base_url))
            .addCallAdapterFactory(get<CoroutineCallAdapterFactory>())
            .addConverterFactory(get<GsonConverterFactory>())
            .build()
    }

    single { OkHttpClient.Builder().build() }

    single { CoroutineCallAdapterFactory() }

    single { GsonConverterFactory.create() }

    single { InMemoryFavoredPostPersistence() as FavoredPostPersistence }
}