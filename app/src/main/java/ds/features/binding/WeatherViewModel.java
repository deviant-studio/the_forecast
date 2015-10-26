package ds.features.binding;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import ds.features.Constants;
import ds.features.db.DB;
import ds.features.db.realm.Weather;

public class WeatherViewModel {

	public Weather data;


	public WeatherViewModel(final long id) {
		this.data = DB.Companion.getInstance().getWeatherModel(id);
	}

	public WeatherViewModel(Weather data) {
		this.data = data;
	}


	public int humidityVisibility() {
		return data.getHumidity() == null ? View.GONE : View.VISIBLE;
	}


	@BindingAdapter("app:iconUrl")
	public static void iconUrl(ImageView img, String url) {
		Picasso.with(img.getContext()).load(String.format(Constants.IMAGES_BASE_URL, url)).into(img);
	}


	@BindingAdapter("app:windDirection")
	public static void windDirection(ImageView img, float deg) {
		img.setRotation(-90);
		img.animate().rotationBy(deg);
	}


	@BindingAdapter("app:windDirectionNow")
	public static void windDirectionNow(ImageView img, float deg) {
		img.setRotation(deg - 90);
	}



}
