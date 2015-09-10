package ds.features.model;

import org.parceler.Parcel;

@Parcel
public class WeatherData {

	public String getIconUrl() {
		return weather != null && weather.length != 0 ? weather[0].icon : "";
	}


	public long dt;
	public Main main;
	public Weather weather[];
	public Clouds clouds;
	public Wind wind;
	public Rain rain;
	public Sys sys;
	public String dt_txt;


	@Parcel
	public static class Main {

		public double temp;
		public double temp_min;
		public double temp_max;
		public double pressure;
		public double sea_level;
		public double grnd_level;
		public int humidity;
		public double temp_kf;

	}


	@Parcel
	public static class Weather {

		public long id;
		public String main;
		public String description;
		public String icon;

	}


	@Parcel
	public static class Clouds {

		public long all;
	}


	@Parcel
	public static class Wind {

		public float speed;
		public float deg;

	}


	@Parcel
	public static class Rain {
	}


	@Parcel
	public static class Sys {

		public String pod;

	}
}