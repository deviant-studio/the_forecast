package ds.features

import android.os.Looper

object Utils {

	fun isUiThread(): Boolean = (Thread.currentThread() === Looper.getMainLooper().thread)


}

fun <T>profile(f: () -> T, message: String = "noname"):T {
	var time = System.currentTimeMillis()
	val result=f()
	time = System.currentTimeMillis() - time
	L.v("profile $message: ${time}ms")
	return result
}
