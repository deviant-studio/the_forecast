package ds.features.binding

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.ViewAnimationUtils
import ds.features.L
import ds.features.databinding.ActivityDetailsBinding
import ds.features.db.realm.Weather

class WeatherViewModel() : BaseViewModel<ActivityDetailsBinding>() {

	constructor(w: Weather) : this() {
		data = w
	}

	@JvmField var weatherId: Long = 0
	lateinit var data: Weather

	override fun onViewModelCreated() {
		super.onViewModelCreated()
		val id = activity.intent.getLongExtra("weatherId", -1);
		data = db.getWeatherModel(id)
	}

	fun humidityVisibility(): Int {
		return if (data.humidity == null) View.GONE else View.VISIBLE
	}

	fun onClick(v: View) {
		L.v("click")
		val cx = (v.left + v.right) / 2
		val cy = (v.top + v.bottom) / 2
		val initialRadius = v.width

		val anim = ViewAnimationUtils.createCircularReveal(binding.card, cx, cy, initialRadius.toFloat(), 0f)
		anim.addListener(object : AnimatorListenerAdapter() {
			override fun onAnimationEnd(animation: Animator) {
				binding.card.visibility = View.INVISIBLE
				activity.finish()
			}
		})
		anim.duration = 500
		anim.start()
	}

	override fun toggleProgress(enable: Boolean) {
	}

}
