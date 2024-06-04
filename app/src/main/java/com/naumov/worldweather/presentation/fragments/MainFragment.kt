package com.naumov.worldweather.presentation.fragments

import android.annotation.SuppressLint
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
import com.naumov.worldweather.R
import com.naumov.worldweather.databinding.FragmentMainBinding
import com.naumov.worldweather.presentation.state.WeatherState
import com.naumov.worldweather.presentation.viewmodel.WeatherViewModel
import java.util.Locale

class MainFragment : Fragment() {
    private val viewModel: WeatherViewModel by activityViewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

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

    @SuppressLint("SetTextI18n")
    private fun initViews(state: WeatherState) {
        with(binding) {
            state.weatherInfo?.currentWeatherData?.let {
                with(state.weatherInfo.currentWeatherData) {
                    currentTemperature.text = "${temperatureCelsius.toInt()}°"
                    currentWindText.text = "${windSpeed.toInt()} м/с"
                    currentPressureText.text = "${(pressure*0.75).toInt()} мм.рт.ст."
                    currentHumidityText.text = "${humidity.toInt()} %"
                    currentApparentTemperature.text = "По ощущениям ${apparentTemperature.toInt()}°"
                    icWeatherType.setImageResource(weatherType.iconRes)
                }
            }
            swipeRefreshLayout.setOnRefreshListener { viewModel.loadWeatherInfo() }
            hourlyForecastRecycler.layoutManager = LinearLayoutManager(requireContext())
            hourlyForecastRecycler.adapter = state.weatherInfo?.weatherDataPerDay?.let {
                ForecastAdapter(it.getValue(0))
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun getLocationName(state: WeatherState) {
        val geoCoder = Geocoder(requireContext(), Locale.getDefault())
        state.location?.let {
            val addressList: List<Address>? = geoCoder.getFromLocation(
                it.latitude,
                it.longitude,
                1
            )
            binding.currentCity.text =
                addressList?.first()?.locality ?: getString(R.string.current_location)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}