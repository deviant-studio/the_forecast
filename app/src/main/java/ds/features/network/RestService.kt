package ds.features.network


import ds.features.model.CurrWeatherData
import ds.features.model.Forecast16
import ds.features.model.Forecast5
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

public class RestService(@Inject @JvmField var api: RestApi) {

	val city = "Kharkiv"

	fun getWeather(): Observable<CurrWeatherData> = api.getWeather(city).compose(applySchedulers())

	fun getForecast5(): Observable<Forecast5> = api.getFiveDaysForecast(city).compose(applySchedulers())

	fun getForecast16(): Observable<Forecast16> = api.getSixteenDaysForecast(city, 16).compose(applySchedulers())

	fun <T> applySchedulers(): Observable.Transformer<in T, out T> = Observable.Transformer() {
		it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
	}
}
