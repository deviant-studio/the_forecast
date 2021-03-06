package ds.features.network

import ds.features.model.Forecast16
import ds.features.model.Forecast5
import ds.features.model.CurrWeatherData
import retrofit.http.GET
import retrofit.http.Query
import rx.Observable

public interface RestApi {

    @GET("/weather?units=metric")
    fun getWeather(@Query("q") city: String): Observable<CurrWeatherData>

    @GET("/forecast?units=metric")
    fun getFiveDaysForecast(@Query("q") city: String): Observable<Forecast5>

    @GET("/forecast/daily?units=metric")
    fun getSixteenDaysForecast(@Query("q") city: String, @Query("cnt") days: Int): Observable<Forecast16>
}
