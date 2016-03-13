package ds.features.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import android.view.ViewAnimationUtils
import cz.kinst.jakub.viewmodelbinding.ViewModelBindingConfig
import ds.features.R
import ds.features.binding.WeatherViewModel
import ds.features.databinding.ActivityDetailsBinding

class DetailsActivity : BaseActivity<ActivityDetailsBinding, WeatherViewModel>() {
	override fun getViewModelBindingConfig(): ViewModelBindingConfig {
		return ViewModelBindingConfig(R.layout.activity_details, WeatherViewModel::class.java)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		window.enterTransition = TransitionInflater.from(this).inflateTransition(R.transition.slide_transition)
		super.onCreate(savedInstanceState)
		setupToolBar()
	}

	override fun navigate(way: Int, params: Bundle) {
		super.navigate(way, params)
		val cx = params.getInt("cx")
		val cy = params.getInt("cy")
		val initialRadius = params.getInt("w")

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

}
