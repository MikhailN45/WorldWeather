package com.naumov.worldweather.presentation.fragments

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.naumov.worldweather.databinding.HourlyForecastItemBinding
import com.naumov.worldweather.domain.weather.WeatherData
import java.time.format.DateTimeFormatter

class ForecastAdapter(private val data: List<WeatherData>) :
    RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weatherData = data[position]
        holder.date.text = weatherData.time.format(DateTimeFormatter.ofPattern("HH:mm"))
        holder.icon.setImageResource(weatherData.weatherType.iconRes)
        holder.temperature.text = "${weatherData.temperatureCelsius.toInt()}°"
    }
}