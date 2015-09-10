package ds.features.di;

import dagger.Component;
import ds.features.network.RestService;
import ds.features.ui.BaseActivity;
import ds.features.ui.MainActivity;
import ds.features.ui.WeatherListActivity;

import javax.inject.Singleton;

@Singleton
@Component(
		modules = {
				NetworkModule.class,
				RxModule.class,
				MiscModule.class,
		}
)
public interface MainComponent {

	void inject(MainActivity a);

	void inject(WeatherListActivity a);

	void inject(BaseActivity baseActivity);

	void inject(RestService rs);

}
