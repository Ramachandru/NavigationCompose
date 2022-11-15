package com.example.navigationcomposeapp.view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomProgressIndicator() {
    val size = 240.dp
    val indicatorThickness = 12.dp
    val animationTimeMills = 1200
    val dataUsage: Float = 100f
    val IndicatorColor: Color = Color(0xFF42890F)
    var dataUsageValue by remember {
        mutableStateOf(-1f)
    }
    val animatedDatavalue =
        animateFloatAsState(
            targetValue = dataUsageValue,
            animationSpec = tween(animationTimeMills)
        )
    LaunchedEffect(Unit) {
        dataUsageValue = dataUsage
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.size(size), contentAlignment = Alignment.Center) {
            Canvas(modifier = Modifier.size(size)) {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color.LightGray,
                            Color.White
                        ),
                        radius = (size / 2).toPx(),
                        center = Offset(this.size.width / 2, this.size.height / 2)
                    ), radius = (size / 2).toPx(),
                    center = Offset(this.size.width / 2, this.size.height / 2)
                )
                drawCircle(
                    color = Color.White,
                    radius = (size / 2 - indicatorThickness).toPx(),
                    center = Offset(this.size.width / 2, this.size.height / 2)
                )
                val sweepAngle = animatedDatavalue.value * 360 / 100
                drawArc(
                    color = IndicatorColor,
                    startAngle = -90f,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(indicatorThickness.toPx(), cap = StrokeCap.Round),
                    size = Size(
                        width = (size - indicatorThickness).toPx(),
                        height = (size - indicatorThickness).toPx()
                    ),
                    topLeft = Offset(
                        (indicatorThickness / 2).toPx(),
                        (indicatorThickness / 2).toPx()
                    )
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = animatedDatavalue.value.toInt().toString() + "%",
                    style = TextStyle(
                        fontSize = (animatedDatavalue.value.toInt() / 2).sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}