package ds.features.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.transition.AutoTransition;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import com.f2prateek.dart.Dart;
import ds.features.L;
import ds.features.R;
import ds.features.Utils;
import ds.features.binding.WeatherViewModel;
import ds.features.databinding.ActivityRecyclerBinding;
import ds.features.databinding.ItemWeatherBinding;
import ds.features.db.WeatherFactory;
import ds.features.db.realm.Weather;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.List;

@com.f2prateek.dart.Henson
public class WeatherListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

	public static final int MODE_5_DAYS = 0;
	public static final int MODE_16_DAYS = 1;

	//@InjectExtra("point")
	Point clickPoint;

	private ActivityRecyclerBinding binding;

	private RecyclerAdapter.OnClickListener onItemClickListener = new RecyclerAdapter.OnClickListener() {
		@Override
		public void onClick(final ItemWeatherBinding b) {

			Intent i = Henson.with(WeatherListActivity.this)
			                 .gotoDetailsActivity()
			                 .weatherId(b.getWeather().data.getId())
			                 .build();

			getWindow().getDecorView().setTransitionName("window");
			ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
					WeatherListActivity.this,
					sharedElement(b.image),
					sharedElement(b.temp),
					sharedElement(b.date),
					sharedElement(b.humidity),
					sharedElement(b.pressure),
					sharedElement(b.wind),
					sharedElement(b.windDirection),
					sharedElement(b.root),
					sharedElement(binding.toolbar)

			);
			ActivityCompat.startActivity(WeatherListActivity.this, i, options.toBundle());
		}
	};


	private Pair<View, String> sharedElement(final View v) {
		return new Pair<>(v, ViewCompat.getTransitionName(v));
	}


	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Dart.inject(this);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_recycler);
		binding.setLayoutManager(new LinearLayoutManager(this));
		binding.swipeRefreshLayout.setColorSchemeResources(R.color.primary, R.color.accent);
		binding.setOnRefresh(this);
		initTransitions();


		setupToolBar();


		onRefresh();

	}


	@Override
	protected void toggleProgress(final boolean enable) {
		final SwipeRefreshLayout srl = binding.swipeRefreshLayout;
		srl.postDelayed(() -> srl.setRefreshing(enable), 100);

	}


	private void loadForecasts() {
		toggleProgress(true);
		Observable<List<Weather>> forecast5 = service.getForecast5()
		                                             .observeOn(Schedulers.io())
		                                             .map(w5 -> w5.list)
		                                             .flatMap(Observable::from)
		                                             .map(WeatherFactory.INSTANCE::get)
		                                             .toList();
		Observable<List<Weather>> forecast16 = service.getForecast16()
		                                              .observeOn(Schedulers.io())
		                                              .map(w5 -> w5.list)
		                                              .flatMap(Observable::from)
		                                              .map(WeatherFactory.INSTANCE::get)
		                                              .toList();

		Observable.concat(forecast5, forecast16)

		          .doOnNext(items -> db().saveWeatherModel(items))
		          .flatMap(Observable::from)
		          .map(WeatherViewModel::new)
		          .toList()
		          .compose(service.<List<WeatherViewModel>>applySchedulers())
		          .compose(bindToLifecycle())
		          .subscribe(items -> {
			          L.i("on items!");
			          RecyclerAdapter adapter = binding.getAdapter();
			          if (adapter == null) {
				          adapter = new RecyclerAdapter(WeatherListActivity.this, items);
				          binding.setAdapter(adapter);
				          adapter.setOnItemClickListener(onItemClickListener);
			          } else
				          adapter.addItems(items);
			          toggleProgress(false);
		          }, throwable -> {
			          throwable.printStackTrace();
			          toggleProgress(false);
		          });

	}


	private void logThread(final String s) {
		L.v("thread ui? %s [%s]", Utils.INSTANCE$.isUiThread(), s);
	}


	@Override
	public void onRefresh() {
		loadForecasts();
	}


	private void initTransitions() {
		Window window = getWindow();
		window.setEnterTransition(new Explode());
		window.setReturnTransition(new AutoTransition());
		getWindow().setTransitionBackgroundFadeDuration(200);
	}

}
