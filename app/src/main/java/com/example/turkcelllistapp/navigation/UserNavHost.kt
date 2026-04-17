package com.example.turkcelllistapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.turkcelllistapp.ui.screen.UserDetailScreen
import com.example.turkcelllistapp.ui.screen.UserListScreen
import com.example.turkcelllistapp.utils.Constants
import com.example.turkcelllistapp.viewmodel.UserViewModel

/**
 * Uygulamanın navigasyon grafiği.
 * Nested navigation ile ViewModel'i liste ve detay ekranları arasında paylaşır.
 */
@Composable
fun UserNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Constants.NAV_USER_FLOW,
        modifier = modifier
    ) {
        navigation(
            route = Constants.NAV_USER_FLOW,
            startDestination = Constants.NAV_USER_LIST
        ) {
            composable(Constants.NAV_USER_LIST) {
                val parentEntry = remember(it) {
                    navController.getBackStackEntry(Constants.NAV_USER_FLOW)
                }
                val viewModel: UserViewModel = hiltViewModel(parentEntry)
                UserListScreen(
                    viewModel = viewModel,
                    onUserClick = { user ->
                        navController.navigate(Constants.userDetailRoute(user.id))
                    }
                )
            }
            composable(
                route = Constants.NAV_USER_DETAIL,
                arguments = listOf(
                    navArgument(Constants.NAV_ARG_USER_ID) { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(Constants.NAV_USER_FLOW)
                }
                val viewModel: UserViewModel = hiltViewModel(parentEntry)
                val userId = backStackEntry.arguments?.getInt(Constants.NAV_ARG_USER_ID)
                    ?: return@composable
                UserDetailScreen(
                    userId = userId,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
