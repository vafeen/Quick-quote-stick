package ru.vafeen.quickquotestick.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.vafeen.quickquotestick.presentation.components.bottom_bar.BottomBar
import ru.vafeen.quickquotestick.presentation.components.screens.MainScreen
import ru.vafeen.quickquotestick.presentation.components.screens.SettingsScreen
import ru.vafeen.quickquotestick.presentation.components.viewModels.MainActivityViewModel
import ru.vafeen.quickquotestick.presentation.navigation.Navigator
import ru.vafeen.quickquotestick.presentation.navigation.Screen
import ru.vafeen.quickquotestick.presentation.ui.theme.mainLightColor
import ru.vafeen.quickquotestick.presentation.ui.theme.MainTheme
import ru.vafeen.quickquotestick.presentation.ui.theme.Theme
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import ru.vafeen.quickquotestick.presentation.utils.navigateeee

class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainTheme {
                val dark = isSystemInDarkTheme()
                val defaultColor = Theme.colors.mainColor
//                val settings by viewModel.settings.collectAsState()
                val mainColor by remember {
                    derivedStateOf {
                        mainLightColor
//                        settings.getMainColorForThisTheme(isDark = dark) ?: defaultColor
                    }
                }
//                val versionCode by remember { mutableLongStateOf(getVersionCode()) }
//                var updateIsShowed by remember { mutableStateOf(false) }
//                val isUpdateInProcess by viewModel.isUpdateInProcessFlow.collectAsState(false)
//                val downloadedPercentage by viewModel.percentageFlow.collectAsState(0f)
//
//                RequestNotificationPermission()
//
//                if (!updateIsShowed) CheckUpdateAndOpenBottomSheetIfNeed(viewModel = viewModel) {
//                    updateIsShowed = true
//                }
//                if (updateIsShowed && settings.lastDemonstratedVersion < versionCode && settings.releaseBody != "") {
//                    NewVersionInfoBottomSheet(viewModel = viewModel) {
//                        viewModel.saveSettingsToSharedPreferences(
//                            settings.copy(lastDemonstratedVersion = versionCode)
//                        )
//                    }
//                }
                var selectedScreen by remember { mutableStateOf(viewModel.startScreen) }
                val navController = rememberNavController()
                val navigator = object : Navigator {
                    private fun navigate() {
                        navController.navigateeee(selectedScreen)
                    }

                    override fun navigateToMainScreen() {
                        if (selectedScreen != Screen.Main) {
                            selectedScreen = Screen.Main
                            navigate()
                        }
                    }
                    override fun navigateToSettingsScreen() {
                        if (selectedScreen != Screen.Settings) {
                            selectedScreen = Screen.Settings
                            navigate()
                        }
                    }

                }
                val mainScreen by remember {
                    mutableStateOf(
                        MainScreen(
                            navController = navController,
                        )
                    )
                }
                val settingsScreen by remember {
                    mutableStateOf(
                        SettingsScreen(
                            navController = navController,
                        )
                    )
                }
                Scaffold(
                    containerColor = Theme.colors.singleTheme,
                    bottomBar = {
                        BottomBar(
                            navigator = navigator,
                            selectedScreen = selectedScreen,
                            containerColor = mainColor,
                            navController = navController
                        )
                    }) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                    ) {
                        NavHost(
                            modifier = Modifier.weight(1f),
                            navController = navController,
                            startDestination = viewModel.startScreen.route
                        ) {
                            composable(Screen.Main.route) {
                                mainScreen.Content()
                            }
                            composable(Screen.Settings.route) {
                                settingsScreen.Content()
                            }
                        }
//                        if (isUpdateInProcess) UpdateProgress(percentage = downloadedPercentage)
                    }
                }
            }
        }
    }
}
