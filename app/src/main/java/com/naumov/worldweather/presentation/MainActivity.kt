package com.naumov.worldweather.presentation

import android.Manifest
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.naumov.worldweather.R
import com.naumov.worldweather.databinding.ActivityMainBinding
import com.naumov.worldweather.presentation.event.Event
import com.naumov.worldweather.presentation.ui.MainFragment
import com.naumov.worldweather.presentation.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { viewModel.processEvent(Event.RefreshData) }
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        )

        supportFragmentManager.beginTransaction()
            .replace(R.id.activity_fragment_container, MainFragment.newInstance())
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}