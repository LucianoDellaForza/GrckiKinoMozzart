package com.luka.grcki_kino_mozzart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.luka.grcki_kino_mozzart.data.repo.DrawRepo
import com.luka.grcki_kino_mozzart.ui.ComposeApp
import com.luka.grcki_kino_mozzart.ui.theme.GrckiKinoMozzartTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var drawRepo: DrawRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        enableEdgeToEdge()
        setContent {
            GrckiKinoMozzartTheme { //TODO: Add theme colors, typography
                ComposeApp()
            }
        }
    }
}
