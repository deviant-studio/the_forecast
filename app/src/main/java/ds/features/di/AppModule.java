package ds.features.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ds.features.db.DB;

@Module
public class AppModule {

    private Context ctx;


    public AppModule(Context ctx) {
        this.ctx = ctx;
    }

    @Provides
    @Singleton
    public DB provideDB() {
        return new DB(ctx);
    }
}
