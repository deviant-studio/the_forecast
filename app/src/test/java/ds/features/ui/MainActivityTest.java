package ds.features.ui;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import ds.features.App;
import ds.features.BuildConfig;
import ds.features.R;

import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricGradleTestRunner.class)
@Config(application = App.class, constants = BuildConfig.class, sdk = 21)
public class MainActivityTest {

    private MainActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(MainActivity.class).create().get();
    }

    @Test
    public void hasTitleTest() throws Exception {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        Assert.assertFalse(TextUtils.isEmpty(toolbar.getTitle()));
        ShadowActivity shadowActivity = shadowOf(activity);

    }
}