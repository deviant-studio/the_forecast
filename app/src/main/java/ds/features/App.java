package ds.features;

import android.app.Application;
import ds.features.di.DaggerMainComponent;
import ds.features.di.MainComponent;

public class App extends Application {


	private static MainComponent mainComponent;
	private static App instance;


	public static MainComponent getMainComponent() {
		return mainComponent;
	}


	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
	/*	mainComponent = DaggerMainComponent.builder()
		                                   .networkModule(new NetworkModule())
		                                   .build();*/

		mainComponent = DaggerMainComponent.create();
	}


	public static App getInstance() {
		return instance;
	}
}
