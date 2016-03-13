package ds.features.ui

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import cz.kinst.jakub.viewmodelbinding.ViewModel
import cz.kinst.jakub.viewmodelbinding.ViewModelActivity
import ds.features.R

abstract class BaseActivity<T : ViewDataBinding, K : ViewModel<T>> : ViewModelActivity<T, K>() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}


	override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
		if (menuItem.itemId == android.R.id.home) {
			finishAfterTransition()
		}
		return super.onOptionsItemSelected(menuItem)
	}

	protected open fun setupToolBar() {
		val tb = findViewById(R.id.toolbar) as Toolbar?
		if (tb != null) {
			setSupportActionBar(tb)
			supportActionBar!!.setDisplayHomeAsUpEnabled(true)
		}
	}

}
