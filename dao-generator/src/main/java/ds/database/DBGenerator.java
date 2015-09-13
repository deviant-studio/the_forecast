package ds.database;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class DBGenerator {

	public static void main(String[] args) throws Exception {
		Schema schema = new Schema(2, "ds.features.db.gen");

		addWeather(schema);
		schema.enableKeepSectionsByDefault();
		new DaoGenerator().generateAll(schema, "../app/src/main/java");
	}


	private static void addWeather(Schema schema) {
		Entity note = schema.addEntity("Weather");
		note.addIdProperty();
		note.addStringProperty("temp");
		note.addStringProperty("date");
		note.addStringProperty("humidity");
		note.addStringProperty("pressure");
		note.addStringProperty("iconUrl");
		note.addStringProperty("wind");
		note.addFloatProperty("windDir");

	}
}
