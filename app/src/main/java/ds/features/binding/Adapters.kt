package ds.features.binding

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.squareup.picasso.Picasso
import ds.features.Constants

object Adapters {

	@BindingAdapter("app:iconUrl") @JvmStatic
	fun iconUrl(img: ImageView, url: String) {
		Picasso.with(img.context).load(String.format(Constants.IMAGES_BASE_URL, url)).into(img)
	}

	@BindingAdapter("app:windDirection") @JvmStatic
	fun windDirection(img: ImageView, deg: Float) {
		img.rotation = -90f
		img.animate().rotationBy(deg)
	}

	@BindingAdapter("app:windDirectionNow") @JvmStatic
	fun windDirectionNow(img: ImageView, deg: Float) {
		img.rotation = deg - 90
	}
}