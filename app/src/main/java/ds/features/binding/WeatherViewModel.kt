package ds.features.binding

import android.os.Bundle
import android.view.View
import ds.features.L
import ds.features.databinding.ActivityDetailsBinding
import ds.features.db.realm.Weather

class WeatherViewModel() : BaseViewModel<ActivityDetailsBinding>() {

	constructor(w: Weather) : this() {
		data = w
	}

	lateinit var data: Weather

	override fun onViewModelCreated() {
		super.onViewModelCreated()
		val id = view.bundle.getLong("weatherId", -1);
		data = db.getWeatherModel(id)
	}

	fun humidityVisibility(): Int {
		return if (data.humidity == null) View.GONE else View.VISIBLE
	}

	fun onClick(v: View) {
		L.v("click")
		val cx = (v.left + v.right) / 2
		val cy = (v.top + v.bottom) / 2
		val b = Bundle()
		b.putInt("cx",cx)
		b.putInt("cy",cy)
		b.putInt("w",v.width)
		view.navigate(1, b)

	}

	override fun toggleProgress(enable: Boolean) {
	}

}
