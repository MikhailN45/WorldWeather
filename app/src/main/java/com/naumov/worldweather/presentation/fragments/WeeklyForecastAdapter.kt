package com.naumov.worldweather.presentation.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.naumov.worldweather.R
import com.naumov.worldweather.databinding.WeeklyForecastItemBinding
import com.naumov.worldweather.domain.weather.WeeklyForecast

class WeeklyForecastAdapter(private val data: List<WeeklyForecast>, private val context: Context) :
    RecyclerView.Adapter<WeeklyForecastAdapter.ViewHolder>() {

    inner class ViewHolder(binding: WeeklyForecastItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val date: TextView = binding.date
        val icon: ImageView = binding.icWeatherTypeHourly
        val dayTemperature = binding.dayTemperature
        val nightTemperature = binding.nightTemperature
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            WeeklyForecastItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: WeeklyForecastAdapter.ViewHolder, position: Int) {
        val weatherData = data[position]
        holder.date.text = weatherData.date
        holder.icon.setImageResource(weatherData.weatherType.iconRes)
        holder.dayTemperature.text = context.getString(R.string.degree, weatherData.dayTemperature)
        holder.nightTemperature.text = context.getString(R.string.degree, weatherData.nightTemperature)
    }
}