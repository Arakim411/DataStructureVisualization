package com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.compose

import androidx.compose.runtime.Composable
import com.arakim.datastructurevisualization.ui.genericPicker.compose.GenericPickerView
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.VisualizationSetUpPickerPresenter

@Composable
fun VisualizationSetUpPickerView(
    presenter: VisualizationSetUpPickerPresenter,
    content: @Composable () -> Unit
) {

    GenericPickerView(
        optionsPickerPresenter = presenter.genericPickerPresenter,
        content = content,
    )
}