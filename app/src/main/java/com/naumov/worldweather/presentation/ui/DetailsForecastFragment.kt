package com.naumov.worldweather.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.naumov.worldweather.R
import com.naumov.worldweather.databinding.FragmentDetailsForecastBinding
import com.naumov.worldweather.presentation.state.WeatherState
import com.naumov.worldweather.presentation.viewmodel.WeatherViewModel
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
        viewModel.state.observe(viewLifecycleOwner) { state ->
            render(state)
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
            morningTemperatureValue.text = getString(R.string.degree, forecast.temperatureMorning)
            dayTemperatureValue.text = getString(R.string.degree, forecast.temperatureDay)
            eveningTemperatureValue.text = getString(R.string.degree, forecast.temperatureEvening)
            nightTemperatureValue.text = getString(R.string.degree, forecast.temperatureNight)
            //Apparent Temp
            morningApparentTemperature.text =
                getString(R.string.degree, forecast.feelTemperatureMorning)
            dayApparentTemperature.text = getString(R.string.degree, forecast.feelTemperatureDay)
            eveningApparentTemperature.text =
                getString(R.string.degree, forecast.feelTemperatureEvening)
            nightApparentTemperature.text =
                getString(R.string.degree, forecast.feelTemperatureNight)
            //Wind
            morningWindValue.text = getString(R.string.meter_in_seconds, forecast.windMorning)
            dayWindValue.text = getString(R.string.meter_in_seconds, forecast.windDay)
            eveningWindValue.text = getString(R.string.meter_in_seconds, forecast.windEvening)
            nightWindValue.text = getString(R.string.meter_in_seconds, forecast.windNight)
            //Humidity
            morningHumidityValue.text = getString(R.string.percent, forecast.humidityMorning)
            dayHumidityValue.text = getString(R.string.percent, forecast.humidityDay)
            eveningHumidityValue.text = getString(R.string.percent, forecast.humidityEvening)
            nightHumidityValue.text = getString(R.string.percent, forecast.humidityNight)
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