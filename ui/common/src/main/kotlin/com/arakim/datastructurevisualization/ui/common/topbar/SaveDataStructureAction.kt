package com.arakim.datastructurevisualization.ui.common.topbar

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.arakim.datastructurevisualization.ui.common.R.drawable

@Composable
fun SaveDataStructureAction(onClick: () -> Unit) {

    IconButton(onClick = { onClick() }) {
        Icon(painter = painterResource(id = drawable.baseline_save_24), contentDescription = null)
    }
}