package ds.features.db.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import ds.features.db.gen.Weather;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "WEATHER".
*/
public class WeatherDao extends AbstractDao<Weather, Long> {

    public static final String TABLENAME = "WEATHER";

    /**
     * Properties of entity Weather.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Temp = new Property(1, String.class, "temp", false, "TEMP");
        public final static Property Date = new Property(2, String.class, "date", false, "DATE");
        public final static Property Humidity = new Property(3, String.class, "humidity", false, "HUMIDITY");
        public final static Property Pressure = new Property(4, String.class, "pressure", false, "PRESSURE");
        public final static Property IconUrl = new Property(5, String.class, "iconUrl", false, "ICON_URL");
        public final static Property Wind = new Property(6, String.class, "wind", false, "WIND");
        public final static Property WindDir = new Property(7, Float.class, "windDir", false, "WIND_DIR");
    };


    public WeatherDao(DaoConfig config) {
        super(config);
    }
    
    public WeatherDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"WEATHER\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"TEMP\" TEXT," + // 1: temp
                "\"DATE\" TEXT," + // 2: date
                "\"HUMIDITY\" TEXT," + // 3: humidity
                "\"PRESSURE\" TEXT," + // 4: pressure
                "\"ICON_URL\" TEXT," + // 5: iconUrl
                "\"WIND\" TEXT," + // 6: wind
                "\"WIND_DIR\" REAL);"); // 7: windDir
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"WEATHER\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Weather entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String temp = entity.getTemp();
        if (temp != null) {
            stmt.bindString(2, temp);
        }
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(3, date);
        }
 
        String humidity = entity.getHumidity();
        if (humidity != null) {
            stmt.bindString(4, humidity);
        }
 
        String pressure = entity.getPressure();
        if (pressure != null) {
            stmt.bindString(5, pressure);
        }
 
        String iconUrl = entity.getIconUrl();
        if (iconUrl != null) {
            stmt.bindString(6, iconUrl);
        }
 
        String wind = entity.getWind();
        if (wind != null) {
            stmt.bindString(7, wind);
        }
 
        Float windDir = entity.getWindDir();
        if (windDir != null) {
            stmt.bindDouble(8, windDir);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Weather readEntity(Cursor cursor, int offset) {
        Weather entity = new Weather( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // temp
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // date
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // humidity
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // pressure
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // iconUrl
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // wind
            cursor.isNull(offset + 7) ? null : cursor.getFloat(offset + 7) // windDir
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Weather entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTemp(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDate(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setHumidity(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPressure(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setIconUrl(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setWind(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setWindDir(cursor.isNull(offset + 7) ? null : cursor.getFloat(offset + 7));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Weather entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Weather entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
