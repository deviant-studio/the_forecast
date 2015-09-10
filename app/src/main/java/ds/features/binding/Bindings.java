package ds.features.binding;

import android.databinding.BindingAdapter;
import android.text.format.DateUtils;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import ds.features.App;
import ds.features.Constants;

public class Bindings {

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
		img.setRotation(deg-90);
	}


	//@BindingConversion
	public static String fetchDate(long date) {
		final int flags = DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_MONTH | DateUtils.FORMAT_NO_YEAR;
		final String result = DateUtils.formatDateTime(App.getInstance(), date, flags);
		return result;
	}
}
