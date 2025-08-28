package com.gb.opaltest.core.common

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.Date

object DateSerializer : KSerializer<Date> {
    override val descriptor = PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Date {
        val dateString = decoder.decodeString()
        return dateString.stringToDate(format = DATE_FORMAT_SERVER)
    }

    override fun serialize(encoder: Encoder, value: Date) {
        val dateString = value.dateToString(format = DATE_FORMAT_SERVER)
        encoder.encodeString(dateString)
    }
}