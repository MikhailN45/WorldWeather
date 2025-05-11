package com.naumov.worldweather.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.naumov.worldweather.R
import com.naumov.worldweather.databinding.WeeklyForecastItemBinding
import com.naumov.worldweather.domain.model.weather.WeeklyForecast

class WeeklyForecastAdapter(val onItemClick: (Int) -> Unit) :
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
        with(holder.binding) {
            root.setOnClickListener { onItemClick(position) }
            date.text = weatherData.date
            icWeatherTypeHourly.setImageResource(weatherData.weatherType.iconRes)
            dayTemperature.text =
                root.context.getString(R.string.degree, weatherData.dayTemperature.toString())
            nightTemperature.text =
                root.context.getString(R.string.degree, weatherData.nightTemperature.toString())
        }

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