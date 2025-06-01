package com.droiddevs.animateanything.component.bouncySwitch

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.geometry.Size as sizeE


@Composable
fun MorphingThumbSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val transition = updateTransition(targetState = checked, label = "SwitchTransition")

    val trackColor by transition.animateColor(label = "TrackColor") { state ->
        if (state) Color(0xFF34C759) else Color(0xFFFF5E7E)
    }

    val thumbOffset by transition.animateDp(
        transitionSpec = {
            spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow)
        },
        label = "ThumbOffset"
    ) { state ->
        if (state) 36.dp else 0.dp
    }

    val thumbWidth by transition.animateDp(label = "ThumbWidth") { state ->
        if (state) 14.dp else 34.dp
    }

    val thumbHeight by transition.animateDp(label = "ThumbHeight") { state ->
        if (state) 28.dp else 34.dp
    }

    val cornerRadius by transition.animateDp(label = "CornerRadius") { state ->
        if (state) 50.dp else 100.dp
    }

    Box(
        modifier = modifier
            .width(80.dp)
            .height(44.dp)
            .drawBehind {
                // Glow shadow simulation
                for (i in 1..3) {
                    val alphaStep = 0.1f * (4 - i)
                    drawRoundRect(
                        color = trackColor.copy(alpha = alphaStep),
                        topLeft = Offset(-i * 4f, -i * 4f),
                        size = sizeE(size.width + i * 8f, size.height + i * 8f),
                        cornerRadius = CornerRadius(size.height / 2 + i * 2f)
                    )
                }
            }
            .clip(RoundedCornerShape(22.dp))
            .background(trackColor)
            .clickable { onCheckedChange(!checked) }
            .padding(horizontal = 5.dp, vertical = 5.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .width(thumbWidth)
                .height(thumbHeight)
                .offset(x = if (!checked) thumbOffset else thumbOffset + 10.dp)
                .clip(RoundedCornerShape(cornerRadius))
                .then(
                    if (checked) {
                        Modifier.background(Color.White)
                    } else {
                        Modifier
                            .background(Color.Transparent, CircleShape)
                            .border(6.dp, Color.White, CircleShape)
                    }
                )
        )
    }
}


@Composable
fun SwitchDemo() {
    var isOn by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MorphingThumbSwitch(checked = isOn, onCheckedChange = { isOn = it })

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = if (isOn) "Switch is ON" else "Switch is OFF")
    }
}
