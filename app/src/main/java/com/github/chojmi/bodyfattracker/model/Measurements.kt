package com.github.chojmi.bodyfattracker.model

import com.github.chojmi.bodyfattracker.utils.Constans.Companion.ABDOMEN_JPG_URL
import com.github.chojmi.bodyfattracker.utils.Constans.Companion.CHEST_JPG_URL
import com.github.chojmi.bodyfattracker.utils.Constans.Companion.THIGH_JPG_URL

data class JacksonPollock3(val time: Long,
                           val results: Map<MeasurementsSite, MeasurementsResult>) {
    val measurementsMethod: MeasurementsMethod = MeasurementsMethod.JACKSON_POLLOCK_3
}

data class MeasurementsResult(val measurementsSite: MeasurementsSite, val size: Int, val measurementsUnit: MeasurementsUnit = MeasurementsUnit.METRICAL)

enum class MeasurementsMethod {
    JACKSON_POLLOCK_3
}

enum class MeasurementsSite(val jpgUrl: String = "") {
    CHEST(CHEST_JPG_URL),
    ABDOMEN(ABDOMEN_JPG_URL),
    THIGH(THIGH_JPG_URL),
    UNKNOWN
}

enum class MeasurementsUnit {
    METRICAL
}