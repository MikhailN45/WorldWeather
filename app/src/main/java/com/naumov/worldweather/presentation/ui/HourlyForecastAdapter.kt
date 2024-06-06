package com.naumov.worldweather.presentation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.naumov.worldweather.R
import com.naumov.worldweather.databinding.HourlyForecastItemBinding
import com.naumov.worldweather.domain.weather.DayWeatherData
import java.time.format.DateTimeFormatter

class HourlyForecastAdapter(private val data: List<DayWeatherData>, private val context: Context) :
    RecyclerView.Adapter<HourlyForecastAdapter.ViewHolder>() {

    inner class ViewHolder(binding: HourlyForecastItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val date: TextView = binding.date
        val icon: ImageView = binding.icWeatherTypeHourly
        val temperature = binding.dayTemperature
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HourlyForecastItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weatherData = data[position]
        holder.date.text = weatherData.time.format(DateTimeFormatter.ofPattern("HH:mm"))
        holder.icon.setImageResource(weatherData.weatherType.iconRes)
        holder.temperature.text = context.getString(R.string.degree, weatherData.temperatureCelsius)
    }
}