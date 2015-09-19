package ds.features.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.transition.Fade;
import android.transition.Transition;
import android.view.View;
import android.view.ViewAnimationUtils;
import ds.features.App;
import ds.features.L;
import ds.features.R;
import ds.features.databinding.ActivityMainBinding;
import ds.features.model.TimeProvider;
import rx.Observable;
import rx.Subscription;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

public class MainActivity extends BaseActivity implements View.OnClickListener {


	@Inject
	TimeProvider time;

	ActivityMainBinding binding;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//binding = ActivityMainBinding.inflate(getLayoutInflater());
		//setContentView(binding.getRoot());
		initTransitions();

		binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
		App.getMainComponent().inject(this);

		setupToolBar();

		binding.setTimer(time);
		binding.setClicker(this);

		loadCurrWeather();

		runTimer();
	}


	@Override
	protected void toggleProgress(final boolean enable) {

	}


	private void loadCurrWeather() {
		final Subscription s = service.getWeather()
		                              .doOnCompleted(() -> L.v("completed"))
		                              .doOnUnsubscribe(() -> L.v("unsubscribed"))
		                              .compose(bindToLifecycle())
				                      .subscribe(s1 -> {
					                      L.v("name=%s, weather=%s", s1.getName(), s1.getMain().getTemp());
					                      binding.setWeather(s1);
				                      }, Throwable::printStackTrace);
		compositeSubscription.add(s);
	}


	private void runTimer() {
		Observable.interval(1, TimeUnit.SECONDS)
		          .subscribe(aLong -> {
			          time.setTime(System.currentTimeMillis());
		          });

	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		compositeSubscription.unsubscribe();
	}


	@Override
	public void onClick(final View v) {
		animateReveal(binding);
	}


	private void animateReveal(final ActivityMainBinding binding) {
		final View view = binding.mist;
		View b = binding.fab;
		int cx = (b.getLeft() + b.getRight()) / 2;
		int cy = (b.getTop() + b.getBottom()) / 2;
		int initialRadius = view.getWidth();
		view.setVisibility(View.VISIBLE);
		Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, initialRadius);
		anim.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				gotoList();
			}
		});
		anim.setDuration(200);
		anim.start();
	}


	private void gotoList() {
		Intent intent = Henson.with(this)
		                      .gotoWeatherListActivity()
		                      .build();

		final ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
				MainActivity.this/*,
				binding.content,
				binding.content.getTransitionName()*/
		);
		startActivity(intent, options.toBundle());
	}


	private void initTransitions() {
		getWindow().setExitTransition(null);
		getWindow().setReenterTransition(new Fade().setDuration(10).addListener(new Transition.TransitionListener() {
			@Override
			public void onTransitionStart(final Transition transition) {

			}


			@Override
			public void onTransitionEnd(final Transition transition) {
				binding.mist.setVisibility(View.INVISIBLE);
			}


			@Override
			public void onTransitionCancel(final Transition transition) {

			}


			@Override
			public void onTransitionPause(final Transition transition) {

			}


			@Override
			public void onTransitionResume(final Transition transition) {

			}
		}));
	}
}
