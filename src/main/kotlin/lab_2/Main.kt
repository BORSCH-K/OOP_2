package lab_2

import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.awt.datatransfer.StringSelection
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
//                        @Serializable(with = DateAsLongSerializer::class)
//                        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
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
class Week(val number: Int, val type: WeekType)

//object WeekTypeSerializer : KSerializer<WeekType> {
//    override val descriptor = SerialDescriptor("Week", StringSelection().descriptor)
//
//}

//@OptIn(ExperimentalSerializationApi::class)
fun main() {
    // 1 ПУНКТ
    val dateObject = SimpleDateFormat(ObjectMapper.dateFormat)
//    println(dateObject.toPattern()) // yyyy.MM.dd
    val date = dateObject.format(Date())
//    println(date) // 2023.03.19

    val lesson = Lesson("Java Date", date)
    val lessonJson = Json.encodeToString(/*Lesson.serializer(DateAsLongSerializer),*/ lesson)

    println(lessonJson) // {"name":"Java Date","date":12}



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


}