package ds.features.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import android.view.ViewAnimationUtils
import ds.features.L
import ds.features.R
import ds.features.binding.WeatherViewModel
import ds.features.databinding.ActivityDetailsBinding

class DetailsActivity : BaseActivity() {

	@JvmField var weatherId: Long = 0


	override fun onCreate(savedInstanceState: Bundle?) {
		window.enterTransition = TransitionInflater.from(this).inflateTransition(R.transition.slide_transition)
		super.onCreate(savedInstanceState)
		weatherId=intent.getLongExtra("weatherId",-1);
		val binder = DataBindingUtil.setContentView<ActivityDetailsBinding>(this, R.layout.activity_details)
		setupToolBar()
		val weather = WeatherViewModel(weatherId)
		binder.weather = weather
		binder.setClicker { v ->
			L.v("click")
			val cx = (v.left + v.right) / 2
			val cy = (v.top + v.bottom) / 2
			val initialRadius = v.width

			val anim = ViewAnimationUtils.createCircularReveal(binder.card, cx, cy, initialRadius.toFloat(), 0f)
			anim.addListener(object : AnimatorListenerAdapter() {
				override fun onAnimationEnd(animation: Animator) {
					binder.card.visibility = View.INVISIBLE
					finish()
				}
			})
			anim.setDuration(500)
			anim.start()
		}
	}


	private fun animateReveal(v: View) {
	}


	override fun toggleProgress(enable: Boolean) {

	}


}
