package ds.features.ui

import android.os.Bundle
import android.transition.TransitionInflater
import cz.kinst.jakub.viewmodelbinding.ViewModelBindingConfig
import ds.features.R
import ds.features.binding.WeatherViewModel
import ds.features.databinding.ActivityDetailsBinding

class DetailsActivity : BaseActivity<ActivityDetailsBinding, WeatherViewModel>() {
	override fun getViewModelBindingConfig(): ViewModelBindingConfig<WeatherViewModel>? {
		return ViewModelBindingConfig(R.layout.activity_details, WeatherViewModel::class.java)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		window.enterTransition = TransitionInflater.from(this).inflateTransition(R.transition.slide_transition)
		super.onCreate(savedInstanceState)
		setupToolBar()
	}

}
