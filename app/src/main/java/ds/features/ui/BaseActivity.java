package ds.features.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import ds.features.App;
import ds.features.R;
import ds.features.db.DB;
import ds.features.network.RestService;
import rx.subscriptions.CompositeSubscription;

import javax.inject.Inject;

public abstract class BaseActivity extends RxAppCompatActivity {

	@Inject
	protected RestService service;
	@Inject
	protected CompositeSubscription compositeSubscription;
	@Inject
	protected DB db;


	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		App.getMainComponent().inject(this);
	}


	abstract protected void toggleProgress(final boolean enable);


	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		if (menuItem.getItemId() == android.R.id.home) {
			finishAfterTransition();
		}
		return super.onOptionsItemSelected(menuItem);
	}


	protected void setupToolBar() {
		Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
		if (tb != null) {
			setSupportActionBar(tb);
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			//getSupportActionBar().setHomeButtonEnabled(true);
		}
	}

	protected DB db(){
		return db;
	}
}
