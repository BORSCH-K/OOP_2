package lab_2

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.SimpleDateFormat
import java.util.*


object ObjectMapper {
    val dateFormat = "yyyy.MM.dd"
}

object DateAsLongSerializer : KSerializer<Date> {
    override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("Date", PrimitiveKind.LONG)

    override fun serialize(encoder: Encoder, value: Date) =
            encoder.encodeLong(value.time)

    override fun deserialize(decoder: Decoder): Date =
            Date(decoder.decodeLong())
}

@Serializable
data class Lesson<Date>(val name: String,
                        @Serializable(with = DateAsLongSerializer::class)
                        val date: Date
)

fun main() {
    // 1 ПУНКТ
    val dateObject = SimpleDateFormat(ObjectMapper.dateFormat)
    val date = dateObject.format(Date())
//    println(date) // 2023.03.19

    val lesson = Lesson("Java Date", Date(date))
    val lessonJson = Json.encodeToString(Lesson.serializer(DateAsLongSerializer), lesson)

    println(lessonJson)


    // 2 ПУНКТ


}