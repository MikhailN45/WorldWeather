package com.naumov.worldweather.presentation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.naumov.worldweather.R
import com.naumov.worldweather.databinding.HourlyForecastItemBinding
import com.naumov.worldweather.domain.weather.DayWeatherData
import java.time.format.DateTimeFormatter

class HourlyForecastAdapter(private val context: Context) :
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weatherData = getItem(position)
        holder.binding.date.text = weatherData.time.format(DateTimeFormatter.ofPattern("HH:mm"))
        holder.binding.icWeatherTypeHourly.setImageResource(weatherData.weatherType.iconRes)
        holder.binding.dayTemperature.text =
            context.getString(R.string.degree, weatherData.temperature)
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