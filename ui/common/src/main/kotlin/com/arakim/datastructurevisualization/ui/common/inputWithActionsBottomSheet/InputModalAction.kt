package com.arakim.datastructurevisualization.ui.common.inputWithActionsBottomSheet

import androidx.annotation.StringRes

data class InputModalAction(@StringRes val titleResId: Int, val onClick: (Number) -> Unit)