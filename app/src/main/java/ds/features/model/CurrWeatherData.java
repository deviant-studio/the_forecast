package ds.features.model;

import java.util.List;

public final class CurrWeatherData {

	public Coord coord;
	public List<Weather> weather;
	public String base;
	public Main main;
	public Wind wind;
	public Clouds clouds;
	public long dt;
	public Sys sys;
	public long id;
	public String name;
	public long cod;


	public static final class Coord {

		public double lon;
		public double lat;
	}


	public static final class Weather {

		public long id;
		public String main;
		public String description;
		public String icon;

	}


	public static final class Main {

		public double temp;
		public long pressure;
		public long humidity;
		public double temp_min;
		public double temp_max;


	}


	public static final class Wind {

		public double speed;
		public float deg;

	}


	public static final class Clouds {

		public long all;

	}


	public static final class Sys {

		public long type;
		public long id;
		public double message;
		public String country;
		public long sunrise;
		public long sunset;


	}
}