package ds.features;

import android.app.Application;
import com.facebook.stetho.Stetho;
import ds.features.di.*;

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

		Stetho.initializeWithDefaults(this);

		/*RealmConfiguration realmConfiguration = new RealmConfiguration
				.Builder(this)
				.build();
		//Realm.deleteRealm(realmConfiguration); // Clean slate
		Realm.setDefaultConfiguration(realmConfiguration); // Make this Realm the default*/

		mainComponent = DaggerMainComponent.builder()
		                                   .networkModule(new NetworkModule())
		                                   .appModule(new AppModule(this))
		                                   .miscModule(new MiscModule())
		                                   .build();

	}


	public static App getInstance() {
		return instance;
	}
}
