package com.example.delrection

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.view.SurfaceHolder

class DerActivity : AppCompatActivity(),SensorsEventListener {

    protected var m_values = FloatArray(3)
    protected var accelerometerValues: FloatArray? = null
    protected var geomagneticMatrix: FloatArray? = null
    fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> accelerometerValues = event.values.clone()
            Sensor.TYPE_MAGNETIC_FIELD -> geomagneticMatrix = event.values.clone()
            else -> {
            }
        }

        if (geomagneticMatrix != null && accelerometerValues != null) {

            val R = FloatArray(16)
            val I = FloatArray(16)

            SensorManager.getRotationMatrix(R, I, accelerometerValues, geomagneticMatrix)
            SensorManager.getOrientation(R, m_values)
            geomagneticMatrix = null
            accelerometerValues = null
        }
    }

    fun getvalue(idx0: Int): Float {
        return m_values[idx0]
    }

    fun registsensor() {
        regist(m_SensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)
        regist(m_SensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun unregist() {
        m_SensorManager.unregisterListener(this)
    }
}
