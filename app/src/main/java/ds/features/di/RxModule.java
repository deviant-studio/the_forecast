package ds.features.di;

import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

@Module
public class RxModule {

	@Provides
	CompositeSubscription provideCompositeSubscription() {
		return new CompositeSubscription();
	}
}
