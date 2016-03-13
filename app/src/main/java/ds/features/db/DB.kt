package ds.features.db

import android.content.Context
import ds.features.App
import ds.features.L
import ds.features.Utils
import ds.features.db.realm.Weather
import io.realm.Realm

class DB constructor(val context: Context) {

	private val realm: Realm by lazy {
		threadLocalRealm.get()
	}

	private val threadLocalRealm = object : ThreadLocal<Realm>() {
		override fun initialValue(): Realm {
			return Realm.getInstance(context)
		}
	}

	fun saveWeatherModel(list: List<Weather>) {
		L.v("ui? %s", Utils.isUiThread())
		realm.executeTransaction {
			for (w in list) {
				val entity = realm.where(Weather::class.java).equalTo("id", w.id).findFirst()
				L.v("entity=$entity")
				if (entity == null || w.humidity != null) {
					realm.copyToRealmOrUpdate(w)
				}
			}
		}

	}

	fun getWeatherModel(id: Long): Weather {
		L.v("ui? %s", Utils.isUiThread())
		return realm.where(Weather::class.java).equalTo("id", id).findFirst()

	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	companion object {
		val instance: DB by lazy { DB(App.getInstance()) }
	}
}
