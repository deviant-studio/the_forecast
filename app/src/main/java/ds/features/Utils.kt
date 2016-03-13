package ds.features

import android.databinding.ObservableField
import android.os.Looper
import ds.features.binding.BaseViewModel
import ds.features.binding.ModelViewLifecycleEvent
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

object Utils {

	fun isUiThread(): Boolean = (Thread.currentThread() === Looper.getMainLooper().thread)

}

fun <T> Any.profile(f: () -> T, message: String = "noname"): T {
	var time = System.currentTimeMillis()
	val result = f()
	time = System.currentTimeMillis() - time
	L.v("profile $message: ${time}ms")
	return result
}

operator fun <T> ObservableField<T>.invoke(t: T) = set(t)
operator fun <T> ObservableField<T>.invoke() = get()

fun <T> Observable<T>.bindUntilDestroyed(vm: BaseViewModel<*>): Observable<T> = compose(vm.bindUntilEvent<T>(ModelViewLifecycleEvent.DESTROYED))
fun <T> Observable<T>.applySchedulers(): Observable<T> = compose { it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }
