package com.example.buildingsurvey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.buildingsurvey.data.workmanager.CustomWorkManager
import com.example.buildingsurvey.ui.navigation.AppNavHost
import com.example.buildingsurvey.ui.theme.BuildingSurveyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var workManager: CustomWorkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            workManager.setWorkers()
        }
        setContent {
            val navController = rememberNavController()
            BuildingSurveyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavHost(
                        modifier = Modifier,
                        navController = navController
                    )
                }
            }
        }
    }
}