package com.naumov.worldweather.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.naumov.worldweather.R
import com.naumov.worldweather.databinding.HourlyForecastItemBinding
import com.naumov.worldweather.domain.model.weather.DayWeatherData
import java.time.format.DateTimeFormatter

class HourlyForecastAdapter :
    ListAdapter<DayWeatherData, HourlyForecastAdapter.ViewHolder>(HourlyDiffCallback()) {

    inner class ViewHolder(val binding: HourlyForecastItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HourlyForecastItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder.binding) {
        val weatherData = getItem(position)
        date.text = weatherData.time.format(DateTimeFormatter.ofPattern("HH:mm"))
        icWeatherTypeHourly.setImageResource(weatherData.weatherType.iconRes)
        dayTemperature.text = root.context.getString(R.string.degree, weatherData.temperature.toString())
    }
}

class HourlyDiffCallback : DiffUtil.ItemCallback<DayWeatherData>() {
    override fun areItemsTheSame(oldItem: DayWeatherData, newItem: DayWeatherData): Boolean {
        return (oldItem.temperature == newItem.temperature)
                && (oldItem.weatherType == newItem.weatherType)
    }

    override fun areContentsTheSame(oldItem: DayWeatherData, newItem: DayWeatherData): Boolean {
        return oldItem == newItem
    }
}