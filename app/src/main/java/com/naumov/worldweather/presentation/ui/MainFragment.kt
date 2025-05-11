package com.naumov.worldweather.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.naumov.worldweather.R
import com.naumov.worldweather.databinding.FragmentMainBinding
import com.naumov.worldweather.presentation.event.Event
import com.naumov.worldweather.presentation.state.WeatherState
import com.naumov.worldweather.presentation.viewmodel.WeatherViewModel
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private val viewModel: WeatherViewModel by activityViewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var hourlyForecastAdapter: HourlyForecastAdapter
    private lateinit var weeklyForecastAdapter: WeeklyForecastAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMainBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        hourlyForecastAdapter = HourlyForecastAdapter()
        weeklyForecastAdapter = WeeklyForecastAdapter { position ->
            viewModel.processEvent(Event.WeeklyForecastDayPressed(position))
            findNavController().navigate(R.id.action_mainFragment_to_detailsForecastFragment)
        }

        with(binding) {
            hourlyForecastRecycler.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            hourlyForecastRecycler.adapter = hourlyForecastAdapter
            weeklyForecastRecycler.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            weeklyForecastRecycler.adapter = weeklyForecastAdapter
        }

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
        with(binding) {
            swipeRefreshLayout.isRefreshing = state.isLoading
            currentLocation.text = state.locationName
            hourlyForecastAdapter.submitList(state.hourlyForecast)
            weeklyForecastAdapter.submitList(state.weeklyForecast)

            if (!state.error.isNullOrBlank()) {
                Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun initViews(state: WeatherState) {
        with(binding) {
            swipeRefreshLayout.setOnRefreshListener { viewModel.processEvent(Event.RefreshData) }
            state.weatherInfo?.currentDayWeatherData?.let {
                with(state.weatherInfo.currentDayWeatherData) {
                    currentTemperature.text = getString(R.string.degree, temperature.toString())
                    currentWindText.text =
                        getString(R.string.meter_in_seconds, windSpeed.toString())
                    currentPressureText.text =
                        getString(R.string.millimeters_pressure, pressure.toString())
                    currentHumidityText.text = getString(R.string.percent, humidity.toString())
                    currentApparentTemperature.text =
                        getString(R.string.feels_temp, feelsTemperature.toString())
                    updateTime.text = getString(
                        R.string.update_time, state.lastUpdateTime
                    )
                    weatherTypeText.text = weatherType.weatherDesc
                    icWeatherType.setImageResource(weatherType.iconRes)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}