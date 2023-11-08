package com.daffa.swiftshift.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.daffa.swiftshift.presentation.navigation.SwiftShiftApp
import com.daffa.swiftshift.presentation.ui.theme.SwiftShiftTheme
import com.google.accompanist.adaptive.calculateDisplayFeatures

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SwiftShiftTheme {
                // A surface container using the 'background' color from the theme
                val windowSize = calculateWindowSizeClass(activity = this)
                val displayFeatures = calculateDisplayFeatures(activity = this)

                SwiftShiftApp(
                    windowSize = windowSize,
                    displayFeatures = displayFeatures
                )
            }
        }
    }
}