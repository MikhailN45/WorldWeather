package com.naumov.worldweather.presentation.ui

import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.naumov.worldweather.R
import com.naumov.worldweather.databinding.FragmentMainBinding
import com.naumov.worldweather.presentation.event.Event
import com.naumov.worldweather.presentation.state.WeatherState
import com.naumov.worldweather.presentation.viewmodel.WeatherViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class MainFragment : Fragment() {
    private val viewModel: WeatherViewModel by activityViewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val formatterHourMinute: DateTimeFormatter =
        DateTimeFormatter.ofPattern("HH:mm")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMainBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            render(state)
        }
    }

    private fun render(state: WeatherState) {
        initViews(state)
        getLocationName(state)
        with(binding) {
            forecastScreen.isVisible = !state.isLoading
            swipeRefreshLayout.isRefreshing = state.isLoading
            errorMessage.isVisible = !state.error.isNullOrBlank()
            errorMessage.text = state.error
            currentLocation.text = getLocationName(state)
        }
    }

    private fun initViews(state: WeatherState) {
        with(binding) {
            state.weatherInfo?.currentDayWeatherData?.let {
                with(state.weatherInfo.currentDayWeatherData) {
                    currentTemperature.text = getString(R.string.degree, temperatureCelsius)
                    currentWindText.text = getString(R.string.meter_in_seconds, windSpeed)
                    currentPressureText.text = getString(R.string.millimeters_pressure, pressure)
                    currentHumidityText.text = getString(R.string.percent, humidity)
                    currentApparentTemperature.text =
                        getString(R.string.feels_temp, feelsTemperature)
                    updateTime.text = getString(
                        R.string.update_time, LocalDateTime.now()
                            .format(formatterHourMinute)
                    )
                    weatherTypeText.text = weatherType.weatherDesc
                    icWeatherType.setImageResource(weatherType.iconRes)
                }
            }
            swipeRefreshLayout.setOnRefreshListener { viewModel.processEvent(Event.RefreshData) }
            hourlyForecastRecycler.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            hourlyForecastRecycler.adapter =
                HourlyForecastAdapter(viewModel.getDailyForecast(state), requireContext())
            weeklyForecastRecycler.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            weeklyForecastRecycler.adapter = state.weatherInfo?.weatherDataPerDay?.let {
                WeeklyForecastAdapter(viewModel.getWeeklyForecast(state), requireContext())
            }
        }
    }

    private fun getLocationName(state: WeatherState): String {
        val geoCoder = Geocoder(requireContext(), Locale.getDefault())
        var location = ""
        state.location?.let {
            if (Build.VERSION.SDK_INT >= 33) {
                geoCoder.getFromLocation(it.latitude, it.longitude, 1) { geoList ->
                    location = geoList.first().locality ?: getString(R.string.current_location)
                }
            } else {
                @Suppress("DEPRECATION") val geoList: List<Address>? =
                    geoCoder.getFromLocation(it.latitude, it.longitude, 1)
                location = geoList?.first()?.locality ?: getString(R.string.current_location)
            }
        }
        return location
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}