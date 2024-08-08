package com.example.subscriptionbusapplication.helpers

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateJsonAdapter : TypeAdapter<LocalDate?>() {
    override fun write(out: JsonWriter?, value: LocalDate?) {
        if (value == null) return

        out?.value(value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
    }

    override fun read(`in`: JsonReader?): LocalDate? {

        if (`in`?.peek() == JsonToken.NULL) {
            `in`.nextNull()
            return null
        }

        `in`?.let {
            val string = `in`.nextString()
            return LocalDate.parse(string)
        }
        return null
    }
}