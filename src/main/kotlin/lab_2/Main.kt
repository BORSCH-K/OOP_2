package lab_2

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.SerialDescriptor
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json

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

@JsonSerialize
enum class WeekType { TRAINING, SESSION, HOLIDAY }

//@JsonSerialize(using = WeekTypeSerializer.class)
@Serializable
data class Week(
    val number: Int,
                @Serializable(with = WeekTypeSerializer::class)
    val type: WeekType
)

class WeekTypeSerializerJson : JsonSerializer<WeekType>() {
    override fun serialize(value: WeekType, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeString(
            when (value) {
                WeekType.TRAINING -> "Обучение"
                WeekType.SESSION -> "Сессия"
                WeekType.HOLIDAY -> "Каникулы"
            }
        )
    }
}

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
data class WeekSerialize(val number: Int,
//                         @Serializable(with = WeekTypeSerializer::class.java)
                         val type: WeekType)


fun main() {
//     1 ПУНКТ
    println("1 ПУНКТ")

    val objectMapper = jacksonObjectMapper()
    objectMapper.dateFormat = SimpleDateFormat("yyyy.MM.dd")

    val lesson = Lesson("Java Date", Date())
    val lessonJson = objectMapper.writeValueAsString(lesson)

    // При одновременном использовании двух паттернов,
    // на выходе будет формат из аннотации, указаный в классе Lesson.
    println(lessonJson)

//     2 ПУНКТ
    println("\n2 ПУНКТ")

    val course = Course("Math", Person("Leonard Euler"))
    val courseJson = Json.encodeToString(Course.serializer(), course)

    println(courseJson) // {"name":"Math","tutor":{"firstname":"Leonard","surname":"Euler"}}

    val string = "{\"name\": \"Phys\"}" // + "tutor": null <- еще способ
    println(string)

    // null-типы обрабатываются только в том случае,
    // если есть значение по-умолчанию, иначе выбросится исключение отсутствующего поля.
    try {
        val stringJson = Json.decodeFromString(Course.serializer(), string)
        println(stringJson.name)
        println(stringJson.person)
    } catch (e: SerializationException) { // Исключение отсутствующего поля
        println("ERROR!")
    }


    // 3 ПУНКТ
    println("\n3 ПУНКТ")
    val weekArray = arrayListOf(
        1 to WeekType.TRAINING,
        2 to WeekType.TRAINING,
        3 to WeekType.SESSION,
        4 to WeekType.HOLIDAY
    )
    val weekArrayJson = Json.encodeToString(weekArray)
    println(weekArrayJson) // [{"first":1,"second":"TRAINING"},{"first":2,"second":"TRAINING"},{"first":3,"second":"SESSION"},{"first":4,"second":"HOLIDAY"}]

    val weeks = weekArray.map { Week(it.first, it.second) }
    println(weeks)
    val weeksJson = Json.encodeToString(weeks)
    println(weeksJson)

    val serWeeks = weekArray.map { WeekSerialize(it.first, it.second) }
    println(serWeeks)

// Module - Простой интерфейс для расширений, которые могут быть зарегистрированы с ObjectMapper,
// чтобы обеспечить четко определенный набор расширений для функциональности по умолчанию
// SimpleModule - Module реализация, которая позволяет регистрировать сериализаторы и десериализаторы

    // ЗАЩИТА

    println("ЗАЩИТА")

    val jsonMapper = jacksonObjectMapper() // защита
    val module = SimpleModule()
    // cо строчками НЕ РАБОТАЕТ!!! (Может быть какая-то особенность джексона?)
//    val json = jsonMapper.writeValueAsString(weekArray)
//    println(json)
    module.addSerializer(WeekType::class.java, WeekTypeSerializerJson())
    jsonMapper.registerModule(module)
    println(weeks)
    val serJson = jsonMapper.writeValueAsString(weeks)
    println(serJson)

}