package com.arakim.datastructurevisualization.ui.genericPicker.presenter.model

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.PickerDataType.ColorType
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.PickerDataType.DurationType
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.PickerDataType.NumericType
import com.arakim.datastructurevisualization.ui.util.StringWrapper
import javax.annotation.concurrent.Immutable
import kotlin.time.Duration

@Immutable
data class GenericPickerItem<T: PickerDataType>(
    val id: String,
    val title: StringWrapper,
    val iconResId: Int,
    val pickingDataType: T,
    val group: StringWrapper,
)

@Immutable
sealed interface PickerDataType {

    @Immutable
    data class NumericType(val value: Number, val unit: String) : PickerDataType

    @Immutable
    data class DurationType(
        val value: Duration,
    ) : PickerDataType

    @Immutable
    data class ColorType(val value: Color) : PickerDataType
}