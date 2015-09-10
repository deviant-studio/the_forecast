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



}
