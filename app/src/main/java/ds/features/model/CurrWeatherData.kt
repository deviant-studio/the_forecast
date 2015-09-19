package ds.features.model

data class CurrWeatherData(var coord: CurrWeatherData.Coord,
                           var weather: List<CurrWeatherData.Weather>,
                           var base: String,
                           var main: CurrWeatherData.Main,
                           var wind: CurrWeatherData.Wind,
                           var clouds: CurrWeatherData.Clouds,
                           var dt: Long,
                           var sys: CurrWeatherData.Sys,
                           var id: Long,
                           var name: String,
                           var cod: Long) {




    data class Coord(var lon: Double,
                     var lat: Double)


    data class Weather(var id: Long,
                       var main: String,
                       var description: String,
                       var icon: String)

    data class Main(var temp: Double,
                    var pressure: Long,
                    var humidity: Long,
                    var temp_min: Double)

    data class Wind(var speed: Double,
                    var deg: Float = 0.toFloat())


    data class Clouds(var all: Long)


    data class Sys(
            var type: Long,
            var id: Long,
            var message: Double,
            var country: String,
            var sunrise: Long,
            var sunset: Long
    )


}