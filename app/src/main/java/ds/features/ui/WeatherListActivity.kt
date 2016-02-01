package ds.features.ui

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v4.view.ViewCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.transition.AutoTransition
import android.transition.Explode
import android.view.View
import ds.features.L
import ds.features.R
import ds.features.Utils
import ds.features.binding.WeatherViewModel
import ds.features.databinding.ActivityRecyclerBinding
import ds.features.databinding.ItemWeatherBinding
import ds.features.db.WeatherFactory
import ds.features.db.realm.Weather
import rx.Observable
import rx.functions.Action0
import rx.functions.Action1
import rx.schedulers.Schedulers

class WeatherListActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener {

	private var binding: ActivityRecyclerBinding? = null

	private val onItemClickListener: (ItemWeatherBinding) -> Unit = { b ->
		val i = Intent(this@WeatherListActivity, DetailsActivity::class.java).putExtra("weatherId", b.weather.data.id)

		window.decorView.transitionName = "window"
		val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
				this@WeatherListActivity,
				sharedElement(b.image),
				sharedElement(b.temp),
				sharedElement(b.date),
				sharedElement(b.humidity),
				sharedElement(b.pressure),
				sharedElement(b.wind),
				sharedElement(b.windDirection),
				sharedElement(b.root),
				sharedElement(binding!!.toolbar))
		ActivityCompat.startActivity(this@WeatherListActivity, i, options.toBundle())
	}


	private fun sharedElement(v: View): Pair<View, String> {
		return Pair(v, ViewCompat.getTransitionName(v))
	}


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = DataBindingUtil.setContentView<ActivityRecyclerBinding>(this, R.layout.activity_recycler)
		binding!!.layoutManager = LinearLayoutManager(this)
		binding!!.swipeRefreshLayout.setColorSchemeResources(R.color.primary, R.color.accent)
		binding!!.onRefresh = this
		initTransitions()

		setupToolBar()
		onRefresh()
	}


	override fun toggleProgress(enable: Boolean) {
		val srl = binding!!.swipeRefreshLayout
		srl.postDelayed({ srl.isRefreshing = enable }, 100)

	}


	private fun loadForecasts() {
		toggleProgress(true)
		val forecast5: Observable<List<Weather>> = service!!
				.getForecast5()
				.observeOn(Schedulers.io())
				.map({ w5 -> w5.list })
				.flatMap({ Observable.from(it) })
				.map({ WeatherFactory.get(it) })
				.toList()
		val forecast16: Observable<List<Weather>> = service!!
				.getForecast16()
				.observeOn(Schedulers.io())
				.map({ w5 -> w5.list })
				.flatMap({ Observable.from(it) })
				.map({ WeatherFactory.get(it) })
				.toList()

		Observable.concat(forecast5, forecast16)
				.doOnNext { items -> db?.saveWeatherModel(items) }
				.flatMap({ Observable.from(it) })
				.map({ WeatherViewModel(it) })
				.toList()
				.compose(service!!.applySchedulers())
				.compose(bindToLifecycle())
				.subscribe(Action1 { items ->
					L.i("on items!")
					var adapter: RecyclerAdapter? = binding!!.adapter
					if (adapter == null) {
						adapter = RecyclerAdapter(this@WeatherListActivity, items)
						binding!!.adapter = adapter
						adapter.setOnItemClickListener(onItemClickListener)
					} else
						adapter.addItems(items)
					toggleProgress(false)
				}, Action1 { throwable ->
					throwable.printStackTrace()
					toggleProgress(false)
				}, Action0({ }))

	}


	private fun logThread(s: String) {
		L.v("thread ui? %s [%s]", Utils.isUiThread(), s)
	}


	override fun onRefresh() {
		loadForecasts()
	}


	private fun initTransitions() {
		val window = window
		window.enterTransition = Explode()
		window.returnTransition = AutoTransition()
		window.transitionBackgroundFadeDuration = 200
	}

	companion object {
		val MODE_5_DAYS = 0
		val MODE_16_DAYS = 1
	}

}
