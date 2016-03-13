package ds.features.di

import com.facebook.stetho.okhttp.StethoInterceptor
import com.squareup.okhttp.OkHttpClient
import dagger.Module
import dagger.Provides
import ds.features.network.RestApi
import ds.features.network.RestService
import retrofit.RestAdapter
import retrofit.client.OkClient
import javax.inject.Singleton

@Module
class NetworkModule {

	@Provides
	@Singleton
	internal fun provideRestService(api: RestApi): RestService {
		return RestService(api)
	}

	@Provides
	@Singleton
	internal fun provideRestApi(retrofit: RestAdapter): RestApi {
		return retrofit.create(RestApi::class.java)
	}

	@Provides
	@Singleton
	internal fun provideRestAdapter(client: OkHttpClient): RestAdapter {
		return RestAdapter
				.Builder()
				.setEndpoint(BASE_URL)
				.setClient(OkClient(client))
				.setRequestInterceptor { request -> request.addQueryParam("APPID", API_KEY) }
				.build()
	}

	@Provides
	@Singleton
	internal fun provideOkHttpClient(): OkHttpClient {
		val client = OkHttpClient()
		client.networkInterceptors().add(StethoInterceptor())
		return client
	}

	companion object {

		private val API_KEY = "3a2aae4aa5a564852f108fa99754e5f1"
		private val BASE_URL = "http://api.openweathermap.org/data/2.5"
	}
}
