package com.example.turkcelllistapp.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material.icons.filled.SettingsBrightness
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.turkcelllistapp.data.model.User
import com.example.turkcelllistapp.ui.components.UserItem
import com.example.turkcelllistapp.ui.theme.LocalThemeMode
import com.example.turkcelllistapp.ui.theme.ThemeMode
import com.example.turkcelllistapp.utils.Constants
import com.example.turkcelllistapp.utils.Dimens
import com.example.turkcelllistapp.viewmodel.UserUiState
import com.example.turkcelllistapp.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    viewModel: UserViewModel,
    onUserClick: (User) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val filteredUsers by viewModel.filteredUsers.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val pullState = rememberPullToRefreshState()
    val themeModeState = LocalThemeMode.current

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(Constants.TITLE_USER_LIST) },
                actions = {
                    IconButton(onClick = { themeModeState.toggle() }) {
                        Icon(
                            imageVector = when (themeModeState.mode) {
                                ThemeMode.System -> Icons.Default.SettingsBrightness
                                ThemeMode.Light -> Icons.Default.LightMode
                                ThemeMode.Dark -> Icons.Default.DarkMode
                            },
                            contentDescription = when (themeModeState.mode) {
                                ThemeMode.System -> Constants.THEME_SYSTEM_DESC
                                ThemeMode.Light -> Constants.THEME_LIGHT_DESC
                                ThemeMode.Dark -> Constants.THEME_DARK_DESC
                            }
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val state = uiState) {
                is UserUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                is UserUiState.Success -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        TextField(
                            value = searchQuery,
                            onValueChange = viewModel::onSearchQueryChange,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = Dimens.ScreenPadding,
                                    vertical = Dimens.SearchBarVerticalPadding
                                ),
                            placeholder = {
                                Text(
                                    Constants.SEARCH_PLACEHOLDER,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            },
                            trailingIcon = {
                                AnimatedVisibility(
                                    visible = searchQuery.isNotEmpty(),
                                    enter = fadeIn(),
                                    exit = fadeOut()
                                ) {
                                    IconButton(onClick = { viewModel.onSearchQueryChange("") }) {
                                        Icon(
                                            imageVector = Icons.Default.Clear,
                                            contentDescription = Constants.ACTION_CLEAR
                                        )
                                    }
                                }
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(Dimens.SearchBarCornerRadius),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )

                        PullToRefreshBox(
                            isRefreshing = isRefreshing,
                            onRefresh = { viewModel.refreshUsers() },
                            state = pullState,
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                        ) {
                            if (filteredUsers.isEmpty() && searchQuery.isNotBlank()) {
                                EmptySearchResult()
                            } else {
                                LazyColumn(modifier = Modifier.fillMaxSize()) {
                                    item { Spacer(modifier = Modifier.height(4.dp)) }
                                    items(
                                        items = filteredUsers,
                                        key = { it.id }
                                    ) { user ->
                                        UserItem(
                                            user = user,
                                            onClick = { onUserClick(user) }
                                        )
                                    }
                                    item { Spacer(modifier = Modifier.height(8.dp)) }
                                }
                            }
                        }
                    }
                }

                is UserUiState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(Dimens.ErrorSectionPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.WifiOff,
                            contentDescription = null,
                            modifier = Modifier.height(Dimens.ErrorIconSize),
                            tint = MaterialTheme.colorScheme.error.copy(alpha = Dimens.ErrorIconAlpha)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = Constants.ERROR_CONNECTION,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = state.message,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(onClick = { viewModel.loadUsers() }) {
                            Text(Constants.ACTION_RETRY)
                        }
                    }
                }
            }
        }
    }
}

/**
 * Arama sonucu boş olduğunda gösterilen placeholder.
 */
@Composable
private fun EmptySearchResult() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.ErrorSectionPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.SearchOff,
            contentDescription = null,
            modifier = Modifier.height(Dimens.ErrorIconSize),
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = Dimens.ErrorIconAlpha)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = Constants.MSG_SEARCH_EMPTY_TITLE,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = Constants.MSG_SEARCH_EMPTY_SUBTITLE,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
