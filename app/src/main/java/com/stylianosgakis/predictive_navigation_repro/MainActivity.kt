package com.stylianosgakis.predictive_navigation_repro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
data class B(val someEnum: SomeEnum) {
  enum class SomeEnum {
    A, B;
  }
}

@Composable
private fun App() {
  val navController = rememberNavController()
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
            Button({ navController.navigate(B(B.SomeEnum.A)) }) { Text("Go to B") }
          }
        }
      }
      composable<B> {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
          Column {
            Text("B: ${LocalLifecycleOwner.current.lifecycle.currentStateAsState()}")
            Button(dropUnlessResumed { navController.navigate(A) }) { Text("Go to A") }
          }
        }
      }
    }
  }
}
