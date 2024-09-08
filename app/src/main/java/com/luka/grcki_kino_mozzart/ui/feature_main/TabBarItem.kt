package com.luka.grcki_kino_mozzart.ui.feature_main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class TabBarItem(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String
)