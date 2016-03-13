package ds.features.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
		NetworkModule::class,
		MiscModule::class,
		AppModule::class)
          )
interface MainComponent {
	fun inject(a: DaggerSingletones)

}
