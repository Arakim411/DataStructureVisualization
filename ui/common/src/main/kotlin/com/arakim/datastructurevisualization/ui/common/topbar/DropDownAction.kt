package com.arakim.datastructurevisualization.ui.common.topbar

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.arakim.datastructurevisualization.ui.common.R

@Composable
fun DropDownAction(
    content: DropDownActionScope.() -> Unit
) {
    val isExpanded = remember { mutableStateOf(false) }
    val actionsScope = remember(content) { DropDownActionScope().apply { content() } }

    Box(modifier = Modifier) {
        IconButton(onClick = { isExpanded.value = true }) {
            Icon(painter = painterResource(id = R.drawable.baseline_more_vert_24), contentDescription = null)
        }

        DropdownMenu(
            expanded = isExpanded.value,
            onDismissRequest = { isExpanded.value = false },
        ) {
            actionsScope.actions.forEach {
                DropdownMenuItem(
                    text = { Text(it.first) },
                    onClick = { it.second() })
            }
        }
    }
}


class DropDownActionScope {
    private val _actions = mutableListOf<Pair<String, () -> Unit>>()
    val actions: List<Pair<String, () -> Unit>> = _actions

    fun addAction(text: String, action: () -> Unit) {
        _actions.add(Pair(text, action))
    }


}
