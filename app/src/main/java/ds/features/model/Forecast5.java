package ds.features.model;

import java.util.List;

public class Forecast5 {

	public City city;
	public String cod;
	public double message;
	public long cnt;
	public List<WeatherData> list;


	public static class City {

		public long id;
		public String name;
		public Coord coord;
		public String country;
		public long population;
		public Sys sys;


		public static class Coord {

			public double lon;
			public long lat;
		}


		public static class Sys {

			public long population;

		}
	}


	public static class WeatherData {

		public long dt;
		public Main main;
		public Weather weather[];
		public Clouds clouds;
		public Wind wind;
		public Rain rain;
		public Sys sys;
		public String dt_txt;


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


		public static class Weather {

			public long id;
			public String main;
			public String description;
			public String icon;

		}


		public static class Clouds {

			public long all;
		}


		public static class Wind {

			public float speed;
			public float deg;

		}


		public static class Rain {
		}


		public static class Sys {

			public String pod;

		}
	}

}
