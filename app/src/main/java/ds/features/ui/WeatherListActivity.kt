package ds.features.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.transition.AutoTransition
import android.transition.Explode
import cz.kinst.jakub.viewmodelbinding.ViewModelBindingConfig
import ds.features.R
import ds.features.binding.RecyclerViewModel
import ds.features.databinding.ActivityRecyclerBinding

class WeatherListActivity : BaseActivity<ActivityRecyclerBinding, RecyclerViewModel>() {

	override fun getViewModelBindingConfig(): ViewModelBindingConfig {
		return ViewModelBindingConfig(R.layout.activity_recycler, RecyclerViewModel::class.java)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		initTransitions()
		setupToolBar()

	}

	private fun initTransitions() {
		val window = window
		window.enterTransition = Explode()
		window.returnTransition = AutoTransition()
		window.transitionBackgroundFadeDuration = 200
	}

	override fun navigateUpTo(upIntent: Intent?): Boolean {
		return super.navigateUpTo(upIntent)
	}

	override fun navigate(way: Int, params: Bundle?) {
		super.navigate(way, params)
		val i = Intent(activity, DetailsActivity::class.java)
		ActivityCompat.startActivity(activity, i.putExtra("weatherId", params?.getLong("weatherId")), params)
	}


}
