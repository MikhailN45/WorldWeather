package com.naumov.worldweather.presentation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.naumov.worldweather.R
import com.naumov.worldweather.databinding.WeeklyForecastItemBinding
import com.naumov.worldweather.domain.weather.WeeklyForecast

class WeeklyForecastAdapter(private val context: Context, val onItemClick: (Int) -> Unit) :
    ListAdapter<WeeklyForecast, WeeklyForecastAdapter.ViewHolder>(WeeklyDiffCallback()) {

    inner class ViewHolder(val binding: WeeklyForecastItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            WeeklyForecastItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weatherData = getItem(position)
        holder.binding.root.setOnClickListener { onItemClick(position) }
        holder.binding.date.text = weatherData.date
        holder.binding.icWeatherTypeHourly.setImageResource(weatherData.weatherType.iconRes)
        holder.binding.dayTemperature.text =
            context.getString(R.string.degree, weatherData.dayTemperature)
        holder.binding.nightTemperature.text =
            context.getString(R.string.degree, weatherData.nightTemperature)
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.itemView.setOnClickListener(null)
    }
}

class WeeklyDiffCallback : DiffUtil.ItemCallback<WeeklyForecast>() {
    override fun areItemsTheSame(oldItem: WeeklyForecast, newItem: WeeklyForecast): Boolean {
        return (oldItem.dayTemperature == newItem.dayTemperature)
                && (oldItem.nightTemperature == newItem.nightTemperature)
                && (oldItem.weatherType == newItem.weatherType)
    }

    override fun areContentsTheSame(oldItem: WeeklyForecast, newItem: WeeklyForecast): Boolean {
        return oldItem == newItem
    }
}