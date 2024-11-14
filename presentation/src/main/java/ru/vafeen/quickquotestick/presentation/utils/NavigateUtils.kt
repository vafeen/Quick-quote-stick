package ru.vafeen.quickquotestick.presentation.utils

import androidx.navigation.NavController
import ru.vafeen.quickquotestick.presentation.navigation.Screen

internal fun NavController.navigateeee(route: Screen) {
    popBackStack()
    navigate(route.route)
}