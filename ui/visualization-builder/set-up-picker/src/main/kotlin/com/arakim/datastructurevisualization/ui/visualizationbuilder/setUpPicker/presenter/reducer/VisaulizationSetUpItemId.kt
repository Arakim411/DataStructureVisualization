package com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.reducer


enum class VisualizationSetUpItemId(val idString: String) {
    VertexTimeId("VertexTransitionTimeId"),
    ComparisonTimeId("ComparisonTransitionTimeId"),

    BackgroundColorId("ElementBackgroundId"),
    ShapeColorId("ElementShapeColorId"),
    LineColorId("ElementLineColorId"),
    ConnectionLineColorId("ConnectionLineColorId"),
    ArrowColorId("ConnectionArrowColorId"),
    TextColorId("TextColorId"),

    CircleRadiusId("CircleRadiusSizeId"),
    SquareEdgeId("SquareEdgeSizeId"),
    ElementStrokeId("ElementStroke"),
    LineStrokeId("LineStrokeId"),
    TextSizeId("TextSizeId"),
    ArrowSize("ArrowSizeId"),

}
object Units {
    const val Dp = "dp"
    const val Sp = "sp"
}
