package ds.features.db

import android.text.format.DateUtils
import ds.features.App
import ds.features.L
import ds.features.Utils
import ds.features.db.realm.Weather
import ds.features.model.Forecast16
import ds.features.model.Forecast5

object WeatherFactory {

	operator fun get(data: Forecast5.WeatherData): Weather {
		L.v("weather5 factory ui? %s", Utils.isUiThread())
		val w = Weather()
		w.id = data.dt
		w.iconUrl = data.weather[0].icon
		w.date = fetchDate(data.dt * 1000, true)
		w.temp = "%s°".format(data.main.temp)
		w.humidity = "%s%%".format(data.main.humidity)
		w.pressure = "%shpa".format(data.main.pressure)
		w.wind = "%sm/s".format(data.wind.speed)
		w.windDir = data.wind.deg
		return w
	}


	operator fun get(data: Forecast16.WeatherData): Weather {
		L.v("weather16 factory ui? %s", Utils.isUiThread())
		val w = Weather()
		w.id = data.dt
		w.iconUrl = data.weather[0].icon
		w.date = fetchDate(data.dt * 1000, false)
		w.temp = "%s° — %s°".format((data.temp.min.toInt()), (data.temp.max.toInt()))
		w.humidity = if (data.humidity != 0L) "${data.humidity}%" else null
		w.pressure = "%shpa".format(data.pressure)
		w.wind = "%sm/s".format(data.speed)
		w.windDir = data.deg
		//w.data.rain = String.format("%smm", data.rain);
		return w
	}


	private fun fetchDate(date: Long, withTime: Boolean): String {
		var flags = DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_ABBREV_MONTH or DateUtils.FORMAT_NO_YEAR
		if (withTime) flags = flags or DateUtils.FORMAT_SHOW_TIME
		return DateUtils.formatDateTime(App.getInstance(), date, flags)
	}
}