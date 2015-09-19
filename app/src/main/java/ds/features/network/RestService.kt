package ds.features.network


import com.facebook.stetho.okhttp.StethoInterceptor
import com.squareup.okhttp.OkHttpClient
import ds.features.App
import ds.features.model.CurrWeatherData
import ds.features.model.Forecast16
import ds.features.model.Forecast5
import retrofit.RestAdapter
import retrofit.client.OkClient
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

public class RestService {

    private val API_KEY: String = "3a2aae4aa5a564852f108fa99754e5f1"
    private val BASE_URL: String = "http://api.openweathermap.org/data/2.5"


    val city = "Kharkiv"

    val api: RestApi

    init {
        App.getMainComponent().inject(this)
        val client = OkHttpClient()
        client.networkInterceptors().add(StethoInterceptor())
        val retrofit = RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setClient(OkClient(client))
                .setRequestInterceptor({ it.addQueryParam("APPID", API_KEY) })
                //.setLogLevel(RestAdapter.LogLevel.FULL)
                .build()

        api = retrofit.create(RestApi::class.java)

    }

    fun getWeather(): Observable<CurrWeatherData> {
        val o/*Observable<CurrWeatherData?>*/ = api.getWeather(city)
        //o.compose(applySchedulers())
        return o//.compose(applySchedulers())
    }

    fun getForecast5(): Observable<Forecast5> = api.getFiveDaysForecast(city)//.compose(applySchedulers())

    fun getForecast16(): Observable<Forecast16> = api.getSixteenDaysForecast(city, 16)//.compose(applySchedulers())

    fun <T> applySchedulers(): Observable.Transformer<T, T> = Observable.Transformer() {
        it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}
