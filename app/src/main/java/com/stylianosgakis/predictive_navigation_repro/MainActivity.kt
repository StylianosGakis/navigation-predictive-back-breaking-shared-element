package com.stylianosgakis.predictive_navigation_repro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.currentStateAsState
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      App()
    }
  }
}

@Serializable
object A

@Serializable
object B

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun App() {
  val navController = rememberNavController()
  SharedTransitionLayout {
    Surface(modifier = Modifier.fillMaxSize()) {
      NavHost(
        navController,
        A::class,
        modifier = Modifier.fillMaxSize(),
      ) {
        composable<A> {
          Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column {
              Text("A: ${LocalLifecycleOwner.current.lifecycle.currentStateAsState()}")
              Button({ navController.navigate(B) }) { Text("Go to B") }
              SharedBox( this@composable)
            }
          }
        }
        composable<B> {
          Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column {
              SharedBox( this@composable)
              Text("B: ${LocalLifecycleOwner.current.lifecycle.currentStateAsState()}")
              Button(dropUnlessResumed { navController.navigate(A) }) { Text("Go to A") }
            }
          }
        }
      }
    }
  }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.SharedBox(
  animatedVisibilityScope: AnimatedVisibilityScope,
  modifier: Modifier = Modifier,
) {
  Box(
    modifier
      .sharedElement(
        rememberSharedContentState("SharedBox"),
        animatedVisibilityScope,
      )
      .size(200.dp)
      .background(Color.Red)
  )
}
