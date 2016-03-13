package ds.features.ui

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ds.features.R
import ds.features.binding.WeatherViewModel
import ds.features.databinding.ItemWeatherBinding

class RecyclerAdapter(private val ctx: Context, private val items: MutableList<WeatherViewModel>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
	private var clickListener = fun(b: ItemWeatherBinding): Unit? = null


	fun addItems(newItems: List<WeatherViewModel>) {
		val start = items.size
		items.addAll(newItems)
		notifyItemRangeInserted(start, items.size)
	}


	override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
		val binder = DataBindingUtil.inflate<ItemWeatherBinding>(LayoutInflater.from(ctx), R.layout.item_weather, parent, false)
		binder.setClicker { v -> clickListener(binder) }
		return ViewHolder(binder)
	}


	override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
		viewHolder.binder.weather = items[position]
		viewHolder.binder.executePendingBindings()
	}


	override fun getItemCount(): Int {
		return items.size
	}


	fun setOnItemClickListener(f: (ItemWeatherBinding) -> Unit) {
		clickListener = f
	}


	class ViewHolder(val binder: ItemWeatherBinding) : RecyclerView.ViewHolder(binder.getRoot())

}
