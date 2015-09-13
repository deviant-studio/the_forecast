package ds.features.db;

import android.text.format.DateUtils;
import ds.features.App;
import ds.features.db.gen.Weather;
import ds.features.model.Forecast16;
import ds.features.model.Forecast5;

public class WeatherFactory {

	public static Weather get(Forecast5.WeatherData data) {
		Weather w = new Weather(data.dt);
		w.setIconUrl(data.weather[0].icon);
		w.setDate(fetchDate(data.dt * 1000, true));
		w.setTemp(String.format("%s°", data.main.temp));
		w.setHumidity(String.format("%s%%", data.main.humidity));
		w.setPressure(String.format("%shpa", data.main.pressure));
		w.setWind(String.format("%sm/s", data.wind.speed));
		w.setWindDir(data.wind.deg);
		return w;
	}


	public static Weather get(Forecast16.WeatherData data) {
		Weather w = new Weather(data.dt);
		w.setId(data.dt);
		w.setIconUrl(data.weather[0].icon);
		w.setDate(fetchDate(data.dt * 1000, false));
		w.setTemp(String.format("%s° — %s°", ((int) data.temp.min), ((int) data.temp.max)));
		w.setHumidity(data.humidity != 0 ? String.format("%s%%", data.humidity) : null);
		w.setPressure(String.format("%shpa", data.pressure));
		w.setWind(String.format("%sm/s", data.speed));
		w.setWindDir(data.deg);
		//w.data.rain = String.format("%smm", data.rain);
		return w;
	}


	private static String fetchDate(long date, boolean withTime) {
		int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_MONTH | DateUtils.FORMAT_NO_YEAR;
		if (withTime) flags |= DateUtils.FORMAT_SHOW_TIME;
		return DateUtils.formatDateTime(App.getInstance(), date, flags);
	}
}