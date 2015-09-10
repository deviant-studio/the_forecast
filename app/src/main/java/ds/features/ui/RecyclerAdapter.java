package ds.features.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ds.features.R;
import ds.features.databinding.ItemWeatherBinding;
import ds.features.model.WeatherData;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

	private final List<WeatherData> items;
	private final Context ctx;
	private OnClickListener clickListener;


	public RecyclerAdapter(Context ctx, final List<WeatherData> items) {
		this.ctx = ctx;
		this.items = items;

	}


	@Override
	public ViewHolder onCreateViewHolder(final ViewGroup parent, final int position) {
		final ItemWeatherBinding binder = DataBindingUtil.inflate(LayoutInflater.from(ctx), R.layout.item_weather, parent, false);

		binder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				clickListener.onClick(binder);
			}
		});
		return new ViewHolder(binder);
	}


	@Override
	public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
		viewHolder.binder.setWeather(items.get(position));
	}


	@Override
	public int getItemCount() {
		return items.size();
	}


	public void setOnItemClickListener(OnClickListener l) {
		clickListener=l;

	}


	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static class ViewHolder extends RecyclerView.ViewHolder {

		final ItemWeatherBinding binder;


		ViewHolder(ItemWeatherBinding binding) {
			super(binding.getRoot());
			this.binder = binding;
		}
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public interface OnClickListener {
		void onClick(ItemWeatherBinding binder);
	}
}
