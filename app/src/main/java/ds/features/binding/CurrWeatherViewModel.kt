package ds.features.binding

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.databinding.ObservableField
import android.support.v4.app.ActivityOptionsCompat
import android.view.View
import android.view.ViewAnimationUtils
import ds.features.L
import ds.features.bindUntilDestroyed
import ds.features.databinding.ActivityMainBinding
import ds.features.invoke
import ds.features.model.CurrWeatherData
import ds.features.ui.WeatherListActivity
import rx.Observable
import java.util.concurrent.TimeUnit

class CurrWeatherViewModel : BaseViewModel<ActivityMainBinding>() {

	var weather = ObservableField<CurrWeatherData?>()

	override fun onViewModelCreated() {
		super.onViewModelCreated()
		loadCurrWeather()
		runTimer()
	}

	private fun loadCurrWeather() {
		service
				.getWeather()
				.bindUntilDestroyed(this)
				.subscribe({
					           L.v("name=%s, weather=%s", it.name, it.main.temp)
					           weather(it)
				           },
				           { it.printStackTrace() },
				           { println("onCompleted") }
				          )
	}

	private fun runTimer() {
		Observable
				.interval(1, TimeUnit.SECONDS)
				.subscribe { aLong -> timer.setTime(System.currentTimeMillis()) }
	}

	private fun gotoList() {
		val intent = Intent(context, WeatherListActivity::class.java)
		val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity)
		activity.startActivity(intent, options.toBundle())
	}

	fun onButtonClick(v: View?) {
		animateReveal()
	}

	fun animateReveal() {
		val view = binding.mist
		val b = binding.fab
		val cx = (b.left + b.right) / 2
		val cy = (b.top + b.bottom) / 2
		val initialRadius = view.width
		view.visibility = View.VISIBLE
		val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0f, initialRadius.toFloat())
		anim.addListener(object : AnimatorListenerAdapter() {
			override fun onAnimationEnd(animation: Animator) {
				gotoList()
			}
		})
		anim.duration = 200
		anim.start()
	}

	override fun toggleProgress(enable: Boolean) = Unit
}


