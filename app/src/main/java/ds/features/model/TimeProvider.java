package ds.features.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import ds.features.BR;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeProvider extends BaseObservable {

	Date d;


	public TimeProvider() {
		d = new Date();
	}


	@Bindable
	public String getTime() {
		//d.setTime(System.currentTimeMillis());
		return new SimpleDateFormat("hh:mm:ss").format(d);
	}

	public void setTime(long time){
		d.setTime(time);
		notifyPropertyChanged(BR.time);
	}
}
