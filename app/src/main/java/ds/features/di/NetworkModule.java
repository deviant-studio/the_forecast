package ds.features.di;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ds.features.network.RestApi;
import ds.features.network.RestService;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

@Module
public class NetworkModule {

    private static final String API_KEY = "3a2aae4aa5a564852f108fa99754e5f1";
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5";

    @Provides
    @Singleton
    RestService provideRestService(RestApi api) {
        return new RestService(api);
    }

    @Provides
    @Singleton
    RestApi provideRestApi(RestAdapter retrofit) {
        return retrofit.create(RestApi.class);
    }

    @Provides
    @Singleton
    RestAdapter provideRestAdapter(final OkHttpClient client) {
        return new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setClient(new OkClient(client))
                .setRequestInterceptor(request -> request.addQueryParam("APPID", API_KEY))
                //.setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(new StethoInterceptor());
        return client;
    }
}
