package ds.features.db.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Weather extends RealmObject {

    @PrimaryKey
    private Long id;
    private String temp;
    private String date;
    private String humidity;
    private String pressure;
    private String iconUrl;
    private String wind;
    private Float windDir;


    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(final String temp) {
        this.temp = temp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(final String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(final String pressure) {
        this.pressure = pressure;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(final String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(final String wind) {
        this.wind = wind;
    }

    public Float getWindDir() {
        return windDir;
    }

    public void setWindDir(final Float windDir) {
        this.windDir = windDir;
    }
}
