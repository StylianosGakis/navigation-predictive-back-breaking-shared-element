package com.stylianosgakis.predictive_navigation_repro

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SizeTransform
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute

inline fun <reified T : Any> NavGraphBuilder.navdestination(
  deepLinks: List<NavDeepLink> = emptyList(),
  noinline enterTransition: EnterTransitionFactory? = null,
  noinline exitTransition: ExitTransitionFactory? = null,
  noinline popEnterTransition: EnterTransitionFactory? = enterTransition,
  noinline popExitTransition: ExitTransitionFactory? = exitTransition,
  noinline sizeTransform: SizeTransformFactory? = null,
  noinline content: @Composable T.(NavBackStackEntry) -> Unit,
) {
  composable<T>(
    typeMap = emptyMap(),
    deepLinks = deepLinks,
    enterTransition = enterTransition,
    exitTransition = exitTransition,
    popEnterTransition = popEnterTransition,
    popExitTransition = popExitTransition,
    sizeTransform = sizeTransform,
    content = { navBackStackEntry ->
      CompositionLocalProvider(LocalNavAnimatedVisibilityScope provides this) {
        val destination = navBackStackEntry.toRoute<T>()
        destination.content(navBackStackEntry)
      }
    },
  )
}

val LocalNavAnimatedVisibilityScope: ProvidableCompositionLocal<AnimatedVisibilityScope> = compositionLocalOf {
  error("Must be under a compose `navdestination`")
}

private typealias EnterTransitionFactory =
  @JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?

private typealias ExitTransitionFactory =
  @JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?

private typealias SizeTransformFactory =
  AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards SizeTransform?