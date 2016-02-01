package ds.features.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.transition.Fade
import android.transition.Transition
import android.view.View
import android.view.ViewAnimationUtils
import ds.features.App
import ds.features.L
import ds.features.R
import ds.features.databinding.ActivityMainBinding
import ds.features.model.CurrWeatherData
import ds.features.model.TimeProvider
import rx.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : BaseActivity() {

	@Inject lateinit var time: TimeProvider

	@JvmField var binding: ActivityMainBinding? = null


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		//binding = ActivityMainBinding.inflate(getLayoutInflater());
		//setContentView(binding.getRoot());
		initTransitions()

		binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
		App.getMainComponent().inject(this)

		setupToolBar()

		binding?.timer = time
		binding?.clicker = View.OnClickListener { animateReveal(binding!!) }

		loadCurrWeather()

		runTimer()

		supportActionBar!!.title = "Greetings!"
	}


	override fun toggleProgress(enable: Boolean) {

	}


	private fun loadCurrWeather() {
		val s = service!!.getWeather().doOnCompleted { L.v("completed") }
				.doOnUnsubscribe { L.v("unsubscribed") }
				.compose<CurrWeatherData>(bindToLifecycle<CurrWeatherData>())
				.subscribe({
					           L.v("name=%s, weather=%s", it.name, it.main.temp)
					           binding?.weather = it
				           }, {
					           it.printStackTrace()
				           })
	}


	private fun runTimer() {
		Observable.interval(1, TimeUnit.SECONDS).subscribe { aLong -> time.setTime(System.currentTimeMillis()) }

	}


	override fun onDestroy() {
		super.onDestroy()
	}


	private fun animateReveal(binding: ActivityMainBinding) {
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
		anim.setDuration(200)
		anim.start()
	}


	private fun gotoList() {
		//val intent = Henson.with(this).gotoWeatherListActivity().build()
		val intent = Intent(this, WeatherListActivity::class.java)

		val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@MainActivity)
		startActivity(intent, options.toBundle())
	}


	private fun initTransitions() {
		window.exitTransition = null
		window.reenterTransition = Fade().setDuration(10).addListener(object : Transition.TransitionListener {
			override fun onTransitionStart(transition: Transition) {
			}


			override fun onTransitionEnd(transition: Transition) {
				binding?.mist?.visibility = View.INVISIBLE
			}


			override fun onTransitionCancel(transition: Transition) {
			}


			override fun onTransitionPause(transition: Transition) {
			}


			override fun onTransitionResume(transition: Transition) {
			}
		})
	}
}
