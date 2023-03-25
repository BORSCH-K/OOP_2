package lab_2

import com.fasterxml.jackson.annotation.JsonFormat
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*

//object ObjectMapper {
//    val dateFormat = "yyyy.MM.dd"
//}

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
//                        @Serializable(with = DateAsLongSerializer::class)
//                        @JsonSerialize(as = Date.class)
//                        @DateTimeFormat(pattern="dd/MM/yyyy")
                        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
                        val date: Date
)

@Serializable
data class Course(val name: String, @SerialName("tutor") val person: Person? /*= Person("A B")*/)

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

//@Serializable(WeekTypeSerializer::class)
enum class WeekType { TRAINING, SESSION, HOLIDAY }
@Serializable
class Week(val number: Int, val type: WeekType)

//object WeekTypeSerializer : KSerializer<WeekType> {
//    override val descriptor = SerialDescriptor("Week", StringSelection().descriptor)
//
//}
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

//@OptIn(ExperimentalSerializationApi::class)
fun main() {
    // 1 ПУНКТ

//    val objectMapper = ObjectMapper()
//    val dateFormat = SimpleDateFormat("yyyy.MM.dd")
//    objectMapper.dateFormat = dateFormat
////    println(date) // 2023.03.19
//
//    val lesson = Lesson("Java Date", Date())
//    val lessonJson = objectMapper.writeValueAsString(lesson)
////    val lessonJson = Json.encodeToString(/*Lesson.serializer(DateAsLongSerializer),*/ lesson)
//    println(lesson.date)
//
//    println(lessonJson) // {"name":"Java Date","date":"2023.03.20"}
//    // {"name":"Java Date","date":"2023.03.20"}


    // 2 ПУНКТ

//    val course = Course("Math", Person("Leonard Euler"))
//    val courseJson = Json.encodeToString(Course.serializer(), course)
//
//    println(courseJson) // {"name":"Math","tutor":{"firstname":"Leonard","surname":"Euler"}}
//
//    val string = "{\"name\": \"Phys\"}"
//    println(string)
//
//    /* null-типы обрабатываются только в том случае,
//    если есть значение по-умолчанию, иначе выбросится исключение.*/
//    try {
//        val stringJson = Json.decodeFromString(Course.serializer(), string)
//        println(stringJson.name)
//        println(stringJson.person)
//    } catch (e: MissingFieldException) {
//        println("ERROR!")
//    }


    // 3 ПУНКТ

    val weekArray = arrayListOf(
            1 to WeekType.TRAINING,
            2 to WeekType.TRAINING,
            3 to WeekType.SESSION,
            4 to WeekType.HOLIDAY
    )
    val weekArrayJson = Json.encodeToString(weekArray)
    println(weekArrayJson) // [{"first":1,"second":"TRAINING"},{"first":2,"second":"TRAINING"},{"first":3,"second":"SESSION"},{"first":4,"second":"HOLIDAY"}]

    val weeks = weekArray.map { Week(it.first, it.second) }
//    println(weeks)
    val weeksJson = Json.encodeToString(weeks)
    println(weeksJson)


    val serWeeks = weekArray.map {  WeekSerialize(it.first, it.second) }
    val serWeeksJson = Json.encodeToString(serWeeks)
    println(serWeeksJson)


}