package com.naumov.worldweather.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.naumov.worldweather.R
import com.naumov.worldweather.databinding.FragmentDetailsForecastBinding
import com.naumov.worldweather.presentation.state.WeatherState
import com.naumov.worldweather.presentation.viewmodel.WeatherViewModel
import kotlinx.coroutines.launch
import kotlin.math.abs

class DetailsForecastFragment : Fragment() {
    private val viewModel: WeatherViewModel by activityViewModels()
    private var _binding: FragmentDetailsForecastBinding? = null
    private val binding get() = _binding!!
    private var downX: Float = 0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentDetailsForecastBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setExitGesture(view)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    render(state)
                }
            }
        }
    }

    private fun render(state: WeatherState) {
        initViews(state)
    }

    private fun initViews(state: WeatherState) {
        val forecast = state.detailedDayForecast ?: return
        with(binding) {
            backButton.setOnClickListener { parentFragmentManager.popBackStack() }
            dailyForecastTitle.text = getString(R.string.forecast_date, forecast.date)
            //Icon
            icWeatherTypeMorning.setImageResource(forecast.weatherTypeMorning.iconRes)
            icWeatherTypeDay.setImageResource(forecast.weatherTypeDay.iconRes)
            icWeatherTypeEvening.setImageResource(forecast.weatherTypeEvening.iconRes)
            icWeatherTypeNight.setImageResource(forecast.weatherTypeNight.iconRes)
            //Temp
            morningTemperatureValue.text =
                getString(R.string.degree, forecast.temperatureMorning.toString())
            dayTemperatureValue.text =
                getString(R.string.degree, forecast.temperatureDay.toString())
            eveningTemperatureValue.text =
                getString(R.string.degree, forecast.temperatureEvening.toString())
            nightTemperatureValue.text =
                getString(R.string.degree, forecast.temperatureNight.toString())
            //Apparent Temp
            morningApparentTemperature.text =
                getString(R.string.degree, forecast.feelTemperatureMorning.toString())
            dayApparentTemperature.text =
                getString(R.string.degree, forecast.feelTemperatureDay.toString())
            eveningApparentTemperature.text =
                getString(R.string.degree, forecast.feelTemperatureEvening.toString())
            nightApparentTemperature.text =
                getString(R.string.degree, forecast.feelTemperatureNight.toString())
            //Wind
            morningWindValue.text =
                getString(R.string.meter_in_seconds, forecast.windMorning.toString())
            dayWindValue.text = getString(R.string.meter_in_seconds, forecast.windDay.toString())
            eveningWindValue.text =
                getString(R.string.meter_in_seconds, forecast.windEvening.toString())
            nightWindValue.text =
                getString(R.string.meter_in_seconds, forecast.windNight.toString())
            //Humidity
            morningHumidityValue.text =
                getString(R.string.percent, forecast.humidityMorning.toString())
            dayHumidityValue.text = getString(R.string.percent, forecast.humidityDay.toString())
            eveningHumidityValue.text =
                getString(R.string.percent, forecast.humidityEvening.toString())
            nightHumidityValue.text = getString(R.string.percent, forecast.humidityNight.toString())
            //Pressure
            morningPressureValue.text = forecast.pressureMorning.toString()
            dayPressureValue.text = forecast.pressureDay.toString()
            eveningPressureValue.text = forecast.pressureEvening.toString()
            nightPressureValue.text = forecast.pressureNight.toString()
        }
    }

    private fun setExitGesture(view: View) {
        view.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        downX = event.x
                        return true
                    }

                    MotionEvent.ACTION_UP -> {
                        val deltaX = downX - event.x
                        if (abs(deltaX) > 300) {
                            parentFragmentManager.popBackStack()
                        } else {
                            v?.performClick()
                        }
                        return true
                    }
                }
                return false
            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}