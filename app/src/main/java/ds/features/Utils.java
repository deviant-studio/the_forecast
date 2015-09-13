package ds.features;

import android.os.Looper;

public class Utils {

	public static boolean isUiThread() {return Thread.currentThread() == Looper.getMainLooper().getThread();}
}
