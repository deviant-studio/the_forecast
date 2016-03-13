package ds.features.di

import dagger.Module
import dagger.Provides
import ds.features.model.TimeProvider

import javax.inject.Named
import javax.inject.Singleton

@Module
class MiscModule {
	@Provides
	@Singleton
	internal fun provideTime(): TimeProvider {
		return TimeProvider()
	}

	@Provides
	@Named("city")
	internal fun provideCity(): String {
		return "Kharkiv"
	}
}
