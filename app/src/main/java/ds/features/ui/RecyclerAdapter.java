package ds.features.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import ds.features.R;
import ds.features.binding.WeatherViewModel;
import ds.features.databinding.ItemWeatherBinding;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

	private final List<WeatherViewModel> items;
	private final Context ctx;
	private OnClickListener clickListener;


	public RecyclerAdapter(Context ctx, final List<WeatherViewModel> items) {
		this.ctx = ctx;
		this.items = items;

	}


	public void addItems(List<WeatherViewModel> newItems) {
		final int start = items.size();
		items.addAll(newItems);
		//notifyDataSetChanged();
		notifyItemRangeInserted(start, items.size());
	}


	@Override
	public ViewHolder onCreateViewHolder(final ViewGroup parent, final int position) {
		final ItemWeatherBinding binder = DataBindingUtil.inflate(LayoutInflater.from(ctx), R.layout.item_weather, parent, false);

		binder.setClicker(v -> clickListener.onClick(binder));
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
		clickListener = l;

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
