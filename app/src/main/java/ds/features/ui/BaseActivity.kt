package ds.features.ui

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import ds.features.App
import ds.features.R
import ds.features.db.DB
import ds.features.network.RestService
import javax.inject.Inject

abstract class BaseActivity : RxAppCompatActivity() {

	@Inject @JvmField var service: RestService? = null
	@Inject @JvmField var db: DB? = null


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		App.getMainComponent().inject(this)
	}


	protected abstract fun toggleProgress(enable: Boolean)


	override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
		if (menuItem.itemId == android.R.id.home) {
			finishAfterTransition()
		}
		return super.onOptionsItemSelected(menuItem)
	}


	protected fun setupToolBar() {
		val tb = findViewById(R.id.toolbar) as Toolbar
		if (tb != null) {
			setSupportActionBar(tb)
			supportActionBar!!.setDisplayHomeAsUpEnabled(true)
		}
	}

}
