package com.example.turkcelllistapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.AlternateEmail
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.turkcelllistapp.data.model.User
import com.example.turkcelllistapp.utils.Constants
import com.example.turkcelllistapp.utils.Dimens
import com.example.turkcelllistapp.viewmodel.UserUiState
import com.example.turkcelllistapp.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    userId: Int,
    viewModel: UserViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val user = when (val s = uiState) {
        is UserUiState.Success -> s.users.find { it.id == userId }
        else -> null
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(user?.name ?: Constants.TITLE_USER_DETAIL) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = Constants.ACTION_BACK
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (user == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = when (uiState) {
                        is UserUiState.Loading -> Constants.MSG_LOADING
                        is UserUiState.Error -> Constants.MSG_USER_LOAD_FAILED
                        is UserUiState.Success -> Constants.MSG_USER_NOT_FOUND
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                ProfileHeader(user = user)

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = Constants.TITLE_CONTACT_INFO,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                InfoCard(
                    items = listOf(
                        InfoItem(Icons.Outlined.Email, Constants.LABEL_EMAIL, user.email.lowercase()),
                        InfoItem(Icons.Outlined.Phone, Constants.LABEL_PHONE, user.phone),
                        InfoItem(Icons.Outlined.Language, Constants.LABEL_WEBSITE, user.website),
                        InfoItem(
                            Icons.Outlined.AlternateEmail,
                            Constants.LABEL_USERNAME,
                            "${Constants.USERNAME_PREFIX}${user.username}"
                        )
                    ),
                    modifier = Modifier.padding(horizontal = Dimens.ScreenPadding)
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun ProfileHeader(user: User) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val initial = user.name.trim().firstOrNull()?.uppercaseChar()?.toString()
            ?: Constants.AVATAR_FALLBACK
        Box(
            modifier = Modifier
                .size(Dimens.AvatarSizeLarge)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = initial,
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = user.name,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "${Constants.USERNAME_PREFIX}${user.username}",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

private data class InfoItem(
    val icon: ImageVector,
    val label: String,
    val value: String
)

@Composable
private fun InfoCard(items: List<InfoItem>, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.CardCornerRadius),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(4.dp)) {
            items.forEachIndexed { index, item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.ScreenPadding, vertical = Dimens.ContentPadding),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(Dimens.InfoIconSize)
                            .clip(RoundedCornerShape(Dimens.InfoIconCornerRadius))
                            .background(
                                MaterialTheme.colorScheme.primaryContainer
                                    .copy(alpha = Dimens.IconContainerAlpha)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = null,
                            modifier = Modifier.size(Dimens.InfoIconInnerSize),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Column(
                        modifier = Modifier
                            .padding(start = 14.dp)
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = item.label,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = item.value,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                if (index < items.lastIndex) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Dimens.ScreenPadding)
                            .height(Dimens.DividerHeight)
                            .background(
                                MaterialTheme.colorScheme.outlineVariant
                                    .copy(alpha = Dimens.DividerAlpha)
                            )
                    )
                }
            }
        }
    }
}
