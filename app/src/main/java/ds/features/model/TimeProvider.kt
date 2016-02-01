package ds.features.model

import android.databinding.BaseObservable
import android.databinding.Bindable
import ds.features.BR
import java.text.SimpleDateFormat
import java.util.*

class TimeProvider : BaseObservable() {

	internal var d = Date()

	val time: String
		@Bindable
		get() = SimpleDateFormat("hh:mm:ss").format(d)

	fun setTime(time: Long) {
		d.time = time
		notifyPropertyChanged(BR.time)
	}
}
