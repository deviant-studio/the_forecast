package ds.features.ui

import android.os.Bundle
import android.transition.Fade
import android.transition.Transition
import android.view.View
import cz.kinst.jakub.viewmodelbinding.ViewModelBindingConfig
import ds.features.R
import ds.features.binding.CurrWeatherViewModel
import ds.features.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, CurrWeatherViewModel>() {

	override fun getViewModelBindingConfig(): ViewModelBindingConfig<CurrWeatherViewModel>? {
		return ViewModelBindingConfig(R.layout.activity_main, CurrWeatherViewModel::class.java)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		initTransitions()
		setupToolBar()

	}

	override fun setupToolBar(){
		super.setupToolBar()
		supportActionBar!!.title = "Greetings!"
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
