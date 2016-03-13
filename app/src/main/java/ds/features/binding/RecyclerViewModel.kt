package ds.features.binding

import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v4.view.ViewCompat
import android.view.View
import ds.features.*
import ds.features.databinding.ActivityRecyclerBinding
import ds.features.databinding.ItemWeatherBinding
import ds.features.db.WeatherFactory
import ds.features.db.realm.Weather
import ds.features.ui.RecyclerAdapter
import rx.Observable
import rx.Observable.from

class RecyclerViewModel : BaseViewModel<ActivityRecyclerBinding>() {
	companion object {
		const val NAV_DETAILS = 1
	}

	var adapter: RecyclerAdapter? = null

	private val onItemClickListener: (ItemWeatherBinding) -> Unit = { b ->
		activity.window.decorView.transitionName = "window"
		val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
				activity,
				sharedElement(b.image),
				sharedElement(b.temp),
				sharedElement(b.date),
				sharedElement(b.humidity),
				sharedElement(b.pressure),
				sharedElement(b.wind),
				sharedElement(b.windDirection),
				sharedElement(b.root),
				sharedElement(binding.toolbar))

		val bundle = options.toBundle()
		bundle.putLong("weatherId", b.weather.data.id)
		view.navigate(NAV_DETAILS, bundle)
	}

	private fun sharedElement(v: View): Pair<View, String> {
		return Pair(v, ViewCompat.getTransitionName(v))
	}

	override fun onViewModelCreated() {
		super.onViewModelCreated()
		binding.swipeRefreshLayout.setColorSchemeResources(R.color.primary, R.color.accent)
		onRefresh()
	}

	private fun loadForecasts() {
		toggleProgress(true)
		val forecast5: Observable<List<Weather>> = service
				.getForecast5()
				//.observeOn(Schedulers.io())
				.map({ w5 -> w5.list })
				.flatMap({ from(it) })
				.map({ WeatherFactory[it] })
				.toList()
		val forecast16: Observable<List<Weather>> = service
				.getForecast16()
				//.observeOn(Schedulers.io())
				.map({ w5 -> w5.list })
				.flatMap({ from(it) })
				.map({ WeatherFactory[it] })
				.toList()

		Observable.concat(forecast5, forecast16)
				.doOnNext { items -> db.saveWeatherModel(items) }
				.flatMap({ Observable.from(it) })
				.map({ WeatherViewModel(it) })
				.toList()
				.applySchedulers()
				.bindUntilDestroyed(this)
				.subscribe({ items ->
					           L.i("on items!")
					           if (adapter == null) {
						           adapter = RecyclerAdapter(context, items)
						           notifyChange()
						           adapter?.setOnItemClickListener(onItemClickListener)
					           } else
						           adapter?.addItems(items)
					           toggleProgress(false)
				           }, { throwable ->
					           throwable.printStackTrace()
					           toggleProgress(false)
				           }, ({ }))
	}


	private fun logThread(s: String) {
		L.v("thread ui? %s [%s]", Utils.isUiThread(), s)
	}


	fun onRefresh() {
		loadForecasts()
	}

	override fun toggleProgress(enable: Boolean) {
		val refreshLayout = binding.swipeRefreshLayout
		refreshLayout.postDelayed({ if (hasViewAttached()) refreshLayout.isRefreshing = enable }, 100)

	}
}
