package ds.features.di

import ds.features.App
import ds.features.db.DB
import ds.features.model.TimeProvider
import ds.features.network.RestService
import javax.inject.Inject

/*fun <T> daggerField(): ReadOnlyProperty<Any, T> = object : ReadOnlyProperty<Any, T> {
	override fun getValue(thisRef: Any, property: KProperty<*>): T {
		throw UnsupportedOperationException()
	}
}*/

class DaggerSingletones {
	init {
		App.getMainComponent().inject(this)
	}

	@Inject lateinit var timer: TimeProvider
	@Inject lateinit var service: RestService
	@Inject lateinit var db: DB
}