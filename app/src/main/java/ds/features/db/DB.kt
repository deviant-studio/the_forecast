package ds.features.db

import android.content.Context
import ds.features.App
import ds.features.L
import ds.features.Utils
import ds.features.db.realm.Weather
import io.realm.Realm
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

public class DB constructor(val context: Context) {

	private val realm: Realm by object : ReadOnlyProperty<DB, Realm> {
		override fun getValue(db: DB, property: KProperty<*>): Realm {
			return threadLocalRealm.get()
		}

	}

	private val threadLocalRealm = object : ThreadLocal<Realm>() {
		override fun initialValue(): Realm {
			return Realm.getInstance(context)
		}
	}


	//@DebugLog
	public fun saveWeatherModel(list: List<Weather>) {
		//profile ({
		L.v("ui? %s", Utils.isUiThread())
		realm.executeTransaction {
			for (w in list) {
				val entity = realm.where(Weather::class.java).equalTo(Weather::id.name, w.id).findFirst()
				L.v("entity=$entity")
				if (entity == null || w.humidity != null) {
					realm.copyToRealmOrUpdate(w)
				}
			}
		}
		//}, "set weather")


	}

	//@DebugLog
	public fun getWeatherModel(id: Long): Weather? {
		//val result = profile({
		L.v("ui? %s", Utils.isUiThread())
		return realm.where(Weather::class.java).equalTo(Weather::id.name, id).findFirst()

		//}, "get weather")

		//return result
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	companion object {
		public val instance: DB by lazy { DB(App.getInstance()) }
	}
}

/*public class DB constructor(context: Context) {

    private val session: DaoSession

    init {
        val helper = DaoMaster.DevOpenHelper(context, "db", null)
        val db = helper.getWritableDatabase()
        val daoMaster = DaoMaster(db)
        session = daoMaster.newSession()
        instance = this
    }


    public fun saveWeatherModel(list: List<Weather>) {
        L.v("ui? %s", Utils.isUiThread())
        session.runInTx({
            for (w in list) {
                val entity = session.getWeatherDao().load(w.getId())
                if (entity == null)
                    session.getWeatherDao().insert(w)
            }
        })

    }


    public fun getWeatherModel(id: Long): Weather {
        L.v("ui? %s", Utils.isUiThread())
        return session.getWeatherDao().load(id)
    }

    companion object {

        public var instance: DB? = null
    }
}*/
