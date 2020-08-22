package com.diatoztask

import java.util.*

class Weather {
    var id: Int? = null
    var main: String? = null
    var description: String? = null
    var icon: String? = null
    private val additionalProperties: MutableMap<String, Any> = HashMap()

    fun getAdditionalProperties(): Map<String, Any> {
        return additionalProperties
    }

    fun setAdditionalProperty(name: String, value: Any) {
        additionalProperties[name] = value
    }
}