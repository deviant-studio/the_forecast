package ds.features.di;

import dagger.Module;
import dagger.Provides;
import ds.features.model.TimeProvider;

import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class MiscModule {
	@Provides
	@Singleton
	TimeProvider provideTime() {
		return new TimeProvider();
	}

	@Provides
	@Named("city")
	String provideCity(){
		return "Kharkiv";
	}
}
