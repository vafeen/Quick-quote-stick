package ru.vafeen.quickquotestick.presentation.components.screens

import androidx.activity.compose.BackHandler
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import ru.vafeen.quickquotestick.presentation.components.screens.base.ComposableScreen
import ru.vafeen.quickquotestick.presentation.navigation.Navigator
import ru.vafeen.quickquotestick.presentation.navigation.Screen
import ru.vafeen.quickquotestick.presentation.utils.navigateeee


internal class SettingsScreen(private val navigator: Navigator) : ComposableScreen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        BackHandler {
            navigator.navigateToMainScreen()
        }
    }

}
