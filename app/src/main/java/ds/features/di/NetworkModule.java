package ds.features.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ds.features.network.RestService;

@Module
public class NetworkModule {

	@Provides
	@Singleton
	RestService provideRestService() {
		return new RestService();
	}
}
