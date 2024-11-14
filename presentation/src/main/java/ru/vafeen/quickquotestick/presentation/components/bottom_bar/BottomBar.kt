package ru.vafeen.quickquotestick.presentation.components.bottom_bar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.vafeen.quickquotestick.presentation.navigation.Navigator
import ru.vafeen.quickquotestick.presentation.navigation.Screen
import ru.vafeen.quickquotestick.presentation.utils.navigateeee
import ru.vafeen.quickquotestick.presentation.utils.suitableColor


@Composable
internal fun BottomBar(
    navigator: Navigator,
    selectedScreen: Screen,
    containerColor: Color,
    navController: NavController,
) {
    val colors = NavigationBarItemDefaults.colors(
        unselectedIconColor = containerColor.suitableColor().copy(alpha = 0.5f),
        indicatorColor = containerColor,
        disabledIconColor = containerColor.suitableColor(),
    )
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .height(55.dp),
        containerColor = containerColor
    ) {
        NavigationBarItem(
            modifier = Modifier.weight(1 / 2f),
            selected = selectedScreen == Screen.Main,
            onClick = navigator::navigateToMainScreen,
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Icon1"
                )
            },
            colors = colors,
            enabled = selectedScreen != Screen.Main
        )
        NavigationBarItem(
            modifier = Modifier.weight(1 / 2f),
            selected = selectedScreen == Screen.Settings,
            onClick = navigator::navigateToSettingsScreen,
            icon = {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = "Icon2"
                )
            },
            enabled = selectedScreen != Screen.Settings,
            colors = colors
        )
    }
}