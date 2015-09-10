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
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import com.f2prateek.dart.Dart;
import ds.features.R;
import ds.features.databinding.ActivityRecyclerBinding;
import ds.features.databinding.ItemWeatherBinding;
import ds.features.model.Forecast5;
import rx.functions.Action1;

import java.util.concurrent.TimeUnit;

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
			                 .weather(b.getWeather())
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
		srl.postDelayed(new Runnable() {
			@Override
			public void run() {
				srl.setRefreshing(enable);
			}
		}, 100);

	}


	private void loadForecast5() {
		toggleProgress(true);
		//Observable.concat(service.getForecast5(),service.getForecast16())

		service.getForecast5()
		       .delaySubscription(1000, TimeUnit.MILLISECONDS)
		       .subscribe(
				       new Action1<Forecast5>() {
					       @Override
					       public void call(final Forecast5 forecast) {
						       final RecyclerAdapter adapter = new RecyclerAdapter(WeatherListActivity.this, forecast.list);
						       adapter.setOnItemClickListener(onItemClickListener);
						       binding.setAdapter(adapter);
						       toggleProgress(false);
					       }
				       },
				       new Action1<Throwable>() {
					       @Override
					       public void call(final Throwable throwable) {
						       throwable.printStackTrace();
						       toggleProgress(false);
					       }
				       });

	}


	@Override
	public void onRefresh() {
		loadForecast5();
	}


	private void initTransitions() {
		Window window = getWindow();
		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		int bigRadius = Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels);
		//int bigRadius = binding.getRoot().getWidth();
		//TransitionSet set=new TransitionSet();

		//RevealTransition reveal = new RevealTransition(clickPoint, 0,bigRadius, 1000);
		//reveal.addTarget(binding.content);
		//reveal.excludeTarget(R.id.root,true);
		//set.addTransition(reveal);

		//Transition other=new Slide(Gravity.TOP);
		//other.setDuration(1000);
		//other.setStartDelay(1000);
		//other.excludeTarget(R.id.root,true);
		//set.addTransition(other);

		//set.setOrdering(TransitionSet.ORDERING_SEQUENTIAL);
		//binding.coordinator.setTransitionGroup(true);
		//window.setSharedElementEnterTransition(reveal);
		window.setEnterTransition(new Explode());
		//window.setSharedElementEnterTransition(new Slide());
		window.setReturnTransition(new AutoTransition());
		getWindow().setTransitionBackgroundFadeDuration(200);
	}

}
