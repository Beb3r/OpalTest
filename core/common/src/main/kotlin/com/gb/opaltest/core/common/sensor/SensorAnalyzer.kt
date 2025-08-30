package com.gb.opaltest.core.common.sensor

import android.hardware.SensorManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.mutualmobile.composesensors.SensorDelay
import com.mutualmobile.composesensors.rememberGravitySensorState
import com.mutualmobile.composesensors.rememberMagneticFieldSensorState

// A smoother sensor analyzer for listening to pitch and roll variations based on magnetic and gravity sensors.
@Composable
fun SensorAnalyzer(
    factor: Float = 20f,
    onNewSensorData: (SensorDataUiModel) -> Unit,
) {

    val magneticSensor = rememberMagneticFieldSensorState(sensorDelay = SensorDelay.Game)
    val gravitySensor = rememberGravitySensorState(sensorDelay = SensorDelay.Game)

    LaunchedEffect(magneticSensor, gravitySensor) {
        val magnetic = floatArrayOf(
            magneticSensor.xStrength,
            magneticSensor.yStrength,
            magneticSensor.zStrength
        )
        val gravity = floatArrayOf(gravitySensor.xForce, gravitySensor.yForce, gravitySensor.zForce)

        val r = FloatArray(9)
        val i = FloatArray(9)

        if (SensorManager.getRotationMatrix(r, i, gravity, magnetic)) {
            val orientation = FloatArray(3)
            SensorManager.getOrientation(r, orientation)

            onNewSensorData(
                SensorDataUiModel(
                    roll = orientation[2] * factor,
                    pitch = orientation[1] * factor,
                )
            )
        }
    }
}
