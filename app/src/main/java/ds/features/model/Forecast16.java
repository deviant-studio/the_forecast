package ds.features.model;

public class Forecast16 {

	public City city;
	public String cod;
	public double message;
	public long cnt;
	public java.util.List<WeatherData> list;


	public static class City {

		public long id;
		public String name;
		public Coord coord;
		public String country;
		public long population;


		public static class Coord {

			public double lon;
			public long lat;


			public Coord(double lon, long lat) {
				this.lon = lon;
				this.lat = lat;
			}
		}
	}


	public static class WeatherData {

		public long dt;
		public Temp temp;
		public double pressure;
		public long humidity;
		public Weather weather[];
		public double speed;
		public float deg;
		public long clouds;
		public double rain;


		public static class Temp {

			public double day;
			public double min;
			public double max;
			public double night;
			public double eve;
			public double morn;

		}


		public static class Weather {

			public long id;
			public String main;
			public String description;
			public String icon;

		}
	}
}
