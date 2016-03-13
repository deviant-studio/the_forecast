package ds.features.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ds.features.db.DB
import javax.inject.Singleton

@Module
class AppModule(private val ctx: Context) {

	@Provides
	@Singleton
	fun provideDB(): DB {
		return DB(ctx)
	}
}
