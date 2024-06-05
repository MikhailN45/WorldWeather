package com.naumov.worldweather.presentation.fragments

import android.location.Address
import android.location.Geocoder
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
import com.naumov.worldweather.domain.weather.WeeklyForecast
import com.naumov.worldweather.presentation.state.WeatherState
import com.naumov.worldweather.presentation.viewmodel.WeatherViewModel
import java.time.format.DateTimeFormatter
import java.util.Locale

class MainFragment : Fragment() {
    private val viewModel: WeatherViewModel by activityViewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val formatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("d MMMM", Locale("ru"))

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
        }
    }

    private fun initViews(state: WeatherState) {
        with(binding) {
            state.weatherInfo?.currentWeatherData?.let {
                with(state.weatherInfo.currentWeatherData) {
                    currentTemperature.text = getString(R.string.degree, temperatureCelsius.toInt())
                    currentWindText.text = getString(R.string.meter_in_seconds, windSpeed.toInt())
                    currentPressureText.text =
                        getString(R.string.millimeters_pressure, (pressure * 0.75).toInt())
                    currentHumidityText.text = getString(R.string.percent, humidity.toInt())
                    currentApparentTemperature.text =
                        getString(R.string.apparent_temperature, apparentTemperature.toInt())
                    icWeatherType.setImageResource(weatherType.iconRes)
                }
            }
            swipeRefreshLayout.setOnRefreshListener { viewModel.loadWeatherInfo() }
            hourlyForecastRecycler.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            hourlyForecastRecycler.adapter = state.weatherInfo?.weatherDataPerDay?.let {
                HourlyForecastAdapter(it.getValue(0), requireContext())
            }
            weeklyForecastRecycler.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            weeklyForecastRecycler.adapter = state.weatherInfo?.weatherDataPerDay?.let {
                WeeklyForecastAdapter(getWeeklyForecast(state), requireContext())
            }
        }
    }

    private fun getLocationName(state: WeatherState) {
        val geoCoder = Geocoder(requireContext(), Locale.getDefault())
        state.location?.let {
            val addressList: List<Address>? = geoCoder.getFromLocation(
                it.latitude,
                it.longitude,
                1
            )
            binding.currentLocation.text =
                addressList?.first()?.locality ?: getString(R.string.current_location)
        }
    }

    private fun getWeeklyForecast(state: WeatherState): List<WeeklyForecast> =
        state.weatherInfo?.weatherDataPerDay?.values?.map { list ->
            WeeklyForecast(
                date = list.first().time.toLocalDate().format(formatter),
                dayTemperature = list.first { it.time.hour == 15 }.temperatureCelsius,
                nightTemperature = list.first { it.time.hour == 3 }.temperatureCelsius,
                weatherType = list.first().weatherType
            )
        } ?: emptyList()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}