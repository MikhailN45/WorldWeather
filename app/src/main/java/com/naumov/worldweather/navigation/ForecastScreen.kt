package com.naumov.worldweather.navigation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.naumov.worldweather.presentation.event.Event
import com.naumov.worldweather.presentation.ui.weather_by_day.DayForecast
import com.naumov.worldweather.presentation.ui.weather_overview.TodayOverview
import com.naumov.worldweather.presentation.viewmodel.WeatherViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.naumov.worldweather.R
import com.naumov.worldweather.presentation.ui.theme.LoadingIndicator
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ForecastScreen(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    val coroutineScope = rememberCoroutineScope()
    val currentState = state

    LaunchedEffect(currentState?.error) {
        val error = currentState?.error
        if (!error.isNullOrBlank()) {
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    }

    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            AnimatedPane {
                if (currentState == null || !currentState.isStateFilledSuccessfully) {
                    LoadingIndicator(modifier = Modifier.fillMaxSize())
                } else {
                    TodayOverview(
                        state = currentState,
                        onDaySelected = { dayIndex ->
                            viewModel.processEvent(Event.WeeklyForecastDayPressed(dayIndex))
                            coroutineScope.launch {
                                navigator.navigateTo(
                                    pane = ListDetailPaneScaffoldRole.Detail
                                )
                            }
                        },
                        onRefresh = { viewModel.processEvent(Event.RefreshData) },
                        isRefreshing = currentState.isRefreshing == true
                    )
                }
            }
        },
        detailPane = {
            AnimatedPane {
                if (currentState == null || currentState.isLoading) {
                    LoadingIndicator(modifier = Modifier.fillMaxSize())
                } else {
                    val forecast = currentState.detailedDayForecast
                    if (forecast != null) {
                        DayForecast(
                            state = currentState,
                            onBack = {
                                coroutineScope.launch {
                                    navigator.navigateBack()
                                }
                            }
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                stringResource(R.string.select_day_warning),
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        },
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    )
}