package com.arakim.datastructurevisualization.ui.common.list

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import com.arakim.datastructurevisualization.ui.common.CommonTestTags
import com.arakim.datastructurevisualization.ui.common.list.ListDimens.LeadingContainer
import com.arakim.datastructurevisualization.ui.common.list.ListDimens.MainContainer

@Composable
fun CommonListItem(
    modifier: Modifier = Modifier,
    headLine: String,
    @DrawableRes leadingIcon: Int,
    supportingText: String? = null,
    trailingIcon: Int? = null,
    onTrailingIconClick: (() -> Unit)? = null,
) {
    ListDimens.apply {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(MainContainer.TwoLineHeight)
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = MainContainer.HorizontalPadding),
            contentAlignment = CenterStart,
        ) {
            Row(verticalAlignment = CenterVertically) {
                Icon(
                    modifier = Modifier.size(LeadingContainer.Size),
                    painter = painterResource(id = leadingIcon),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(MainContainer.LeadingTextMargin))
                Column {
                    Text(
                        text = headLine,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                        ),
                    )
                    supportingText?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        )
                    }
                }
                trailingIcon?.also {
                    TrailingIcon(
                        icon = trailingIcon, onClick = onTrailingIconClick
                    )
                }
            }
        }
    }
}

@Composable
private fun RowScope.TrailingIcon(
    icon: Int,
    onClick: (() -> Unit)? = null
) {
    var iconAlpha = remember(icon) { mutableStateOf(0f) }
    val iconAlphaAnim = animateFloatAsState(targetValue = iconAlpha.value, label = "")
    LaunchedEffect(icon) {
        iconAlpha.value = 1f
    }
    Spacer(modifier = Modifier.weight(1f))
    Box(
        modifier = Modifier
            .testTag(CommonTestTags.CommonListItemTrailingIcon)
            .clickable {
                onClick?.invoke()
            }
            .alpha(iconAlphaAnim.value),
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
        )
    }
}