package ds.features.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import ds.features.L
import ds.features.Utils
import ds.features.db.gen.DaoMaster
import ds.features.db.gen.DaoSession
import ds.features.db.gen.Weather
import hugo.weaving.DebugLog

public class DB DebugLog constructor(context: Context) {

    private val session: DaoSession

    init {
        val helper = DaoMaster.DevOpenHelper(context, "db", null)
        val db = helper.getWritableDatabase()
        val daoMaster = DaoMaster(db)
        session = daoMaster.newSession()
        instance = this
    }


    DebugLog
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


    DebugLog
    public fun getWeatherModel(id: Long): Weather {
        L.v("ui? %s", Utils.isUiThread())
        return session.getWeatherDao().load(id)
    }

    companion object {

        public var instance: DB? = null
    }
}
