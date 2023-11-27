package com.arakim.datastructurevisualization.ui.genericPicker.compose.floatingModalItemsView

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arakim.datastructurevisualization.ui.common.genericpicker.R
import com.arakim.datastructurevisualization.ui.common.genericpicker.R.drawable
import com.arakim.datastructurevisualization.ui.genericPicker.compose.pickDataType.ColorView
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.GenericPickerItem
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.PickerDataType.ColorType
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.PickerDataType.DurationType
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.PickerDataType.NumericType
import com.arakim.datastructurevisualization.ui.util.ImmutableList

@Composable
internal fun FloatingModalItemsExpanded(
    items: ImmutableList<GenericPickerItem<*>>,
    onMoreDetailsClick: () -> Unit,
    onItemClick: (GenericPickerItem<*>) -> Unit,
    isExpanded: MutableState<Boolean>,
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.inverseOnSurface, MaterialTheme.shapes.medium)
            .height(80.dp)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        SettingsView(
            modifier = Modifier.fillMaxHeight(),
            onMoreDetailsClick = onMoreDetailsClick,
            onChangeSizeClicked = { isExpanded.value = !isExpanded.value },
            isExpanded = isExpanded.value
        )
        Spacer(modifier = Modifier.width(8.dp))

        AnimatedVisibility(visible = isExpanded.value) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                items.forEach {
                    ModalItem(
                        modifier = Modifier
                            .fillMaxHeight()
                            .clickable { onItemClick(it) },
                        item = it
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}

@Composable
private fun SettingsView(
    modifier: Modifier,
    onMoreDetailsClick: () -> Unit,
    onChangeSizeClicked: () -> Unit,
    isExpanded: Boolean,
) {
    val icon =
        if (isExpanded) R.drawable.baseline_expanded_less_horizontal_24 else R.drawable.baseline_expanded_more_horizontal_24
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Icon(
            modifier = Modifier.clickable { onChangeSizeClicked() },
            painter = painterResource(id = icon),
            contentDescription = null,
        )

        Icon(
            modifier = Modifier.clickable { onMoreDetailsClick() },
            painter = painterResource(id = drawable.outline_more_horiz_24),
            contentDescription = null,
        )
    }
}

@Composable
private fun ModalItem(
    modifier: Modifier,
    item: GenericPickerItem<*>,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Icon(painter = painterResource(id = item.iconResId), contentDescription = null)
        Spacer(modifier = Modifier.height(12.dp))

        when (val dataType = item.pickingDataType) {
            is ColorType -> ColorView(color = dataType.value)

            is DurationType -> Text(
                text = dataType.value.toString(),
                fontSize = 11.sp
            )

            is NumericType -> Text(
                text = dataType.value.toString().plus(" ${dataType.unit}"),
                fontSize = 11.sp
            )
        }
    }
}