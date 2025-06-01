package com.droiddevs.animateanything.component.wiggle

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droiddevs.animateanything.R
import com.droiddevs.animateanything.ui.theme.Pink80

@Composable
fun WiggleClock() {
    var isWiggling by remember { mutableStateOf(true) }
    val angle = remember { Animatable(0f) }

    LaunchedEffect(isWiggling) {
        if (isWiggling) {
            // Start infinite wiggle
            while (isWiggling) {
                angle.animateTo(
                    targetValue = 30f,
                    animationSpec = tween(durationMillis = 250, easing = LinearEasing)
                )
                angle.animateTo(
                    targetValue = -30f,
                    animationSpec = tween(durationMillis = 250, easing = LinearEasing)
                )
            }
        } else {
            // Reset rotation when stopped
            angle.animateTo(0f)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Wiggle animated image
        Image(
            painter = painterResource(id = R.drawable.ic_alarm_clock), // Replace with your image
            contentDescription = "Alarm Clock",
            modifier = Modifier
                .align(Alignment.Center)
                .size(200.dp)
                .rotate(angle.value)
        )

        // Start & Stop buttons
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { isWiggling = false },
                colors = ButtonDefaults.buttonColors(containerColor = Pink80),
                modifier = Modifier
                    .weight(1f)
            ) {
                Text("Stop", fontSize = 18.sp)
            }

            Button(
                onClick = { isWiggling = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                modifier = Modifier
                    .weight(1f)

            ) {
                Text("Start", fontSize = 18.sp)
            }
        }
    }
}
