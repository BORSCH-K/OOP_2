package lab_2

import com.fasterxml.jackson.annotation.JsonFormat
import kotlinx.serialization.KSerializer
import kotlinx.serialization.MissingFieldException
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import java.util.*


class Lesson(
    val name: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    val date: Date
)


@Serializable
data class Course(val name: String, @SerialName("tutor") val person: Person?)  /*= Person("A B")*/

@Serializable
data class Person(
    val firstname: String,
    val surname: String
) {
    constructor(name: String) : this(
        name.substringBefore(" "),
        name.substringAfter(" ")
    )
}

enum class WeekType { TRAINING, SESSION, HOLIDAY }

@Serializable
class Week(val number: Int, val type: WeekType)

object WeekTypeSerializer : KSerializer<WeekType> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("WeekType", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: WeekType) {
        encoder.encodeString(
            when (value) {
                WeekType.TRAINING -> "Обучение"
                WeekType.SESSION -> "Сессия"
                WeekType.HOLIDAY -> "Каникулы"
            }
        )
    }

    override fun deserialize(decoder: Decoder): WeekType {
        return when (decoder.decodeString()) {
            "Обучение" -> WeekType.TRAINING
            "Сессия" -> WeekType.SESSION
            "Каникулы" -> WeekType.HOLIDAY
            else -> error("error!!!")
        }
    }
}

@Serializable
data class WeekSerialize(val number: Int, @Serializable(with = WeekTypeSerializer::class) val type: WeekType)


fun main() {
    // 1 ПУНКТ

//    val objectMapper = jacksonObjectMapper()
//    objectMapper.dateFormat = SimpleDateFormat("yyyy.MM.dd")
//
//    val lesson = Lesson("Java Date", Date())
//    val lessonJson = objectMapper.writeValueAsString(lesson)
//
//    // При одновременном использовании двух паттернов,
//    // на выходе будет формат из аннотации, указаной в классе Lesson.
//    println(lessonJson)

    // 2 ПУНКТ

    val course = Course("Math", Person("Leonard Euler"))
    val courseJson = Json.encodeToString(Course.serializer(), course)

    println(courseJson) // {"name":"Math","tutor":{"firstname":"Leonard","surname":"Euler"}}

    val string = "{\"name\": \"Phys\"}"
    println(string)

    // null-типы обрабатываются только в том случае,
    // если есть значение по-умолчанию, иначе выбросится исключение отсутствующего поля.
    try {
        val stringJson = Json.decodeFromString(Course.serializer(), string)
        println(stringJson.name)
        println(stringJson.person)
    } catch (e: MissingFieldException) { // Исключение отсутствующего поля
        println("ERROR!")
    }
//
//
//    // 3 ПУНКТ
//
//    val weekArray = arrayListOf(
//            1 to WeekType.TRAINING,
//            2 to WeekType.TRAINING,
//            3 to WeekType.SESSION,
//            4 to WeekType.HOLIDAY
//    )
//    val weekArrayJson = Json.encodeToString(weekArray)
//    println(weekArrayJson) // [{"first":1,"second":"TRAINING"},{"first":2,"second":"TRAINING"},{"first":3,"second":"SESSION"},{"first":4,"second":"HOLIDAY"}]
//
//    val weeks = weekArray.map { Week(it.first, it.second) }
////    println(weeks)
//    val weeksJson = Json.encodeToString(weeks)
//    println(weeksJson)
//
//
//    val serWeeks = weekArray.map { WeekSerialize(it.first, it.second) }
//    val serWeeksJson = Json.encodeToString(serWeeks)
//    println(serWeeksJson)

}