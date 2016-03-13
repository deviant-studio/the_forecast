package ds.features.network


import ds.features.applySchedulers
import ds.features.model.CurrWeatherData
import ds.features.model.Forecast16
import ds.features.model.Forecast5
import rx.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestService @Inject constructor(var api: RestApi) {

	val city = "Kharkiv"

	fun getWeather(): Observable<CurrWeatherData> = api.getWeather(city).applySchedulers()

	fun getForecast5(): Observable<Forecast5> = api.getFiveDaysForecast(city).applySchedulers()

	fun getForecast16(): Observable<Forecast16> = api.getSixteenDaysForecast(city, 16).applySchedulers()

}
