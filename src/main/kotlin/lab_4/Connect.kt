package lab_4

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import com.mongodb.ExplainVerbosity
import com.mongodb.client.AggregateIterable
import com.mongodb.client.FindIterable
import org.json.JSONObject
import org.litote.kmongo.KMongo
import org.litote.kmongo.json

val client = KMongo // подключение к серверу
    .createClient("mongodb://root:example@127.0.0.1:27017")
val mongoDatabase = client.getDatabase("lab_4")                     // быстрое подключение к БД на сервере
fun prettyPrintJson(json: String) = // оформление
    println(JSONObject(json).toString(4))

fun prettyPrintCursor(cursor: Iterable<*>) =
    prettyPrintJson("{ result: ${cursor.json} }")

fun prettyPrintExplain(cursor: FindIterable<*>) =
    prettyPrintJson(cursor.explain(ExplainVerbosity.EXECUTION_STATS).json)


fun prettyPrintCursor_4(cursor: Iterable<Temp>) {
    cursor.forEach {
        println(//it
            "Temp(name = ${it.name}, " +
                    "course = ${it.course}, " +
                    "value = ${it.value})"
        )
    }
}

fun prettyPrintCursor_3(cursor: Iterable<Temp>) {
    cursor.forEach {
        println(
            "Temp(name = ${it.name}, " +
                    "course = ${it.course}, " +
                    "value = ${it.value})"
        )
    }
//    println()
}

fun prettyPrintCursor_2(cursor: Iterable<R>) {
    prettyPrintJson2("{ \"result\": ${cursor.json}}")
    prettyPrintJson3(cursor.json)
}

val format = Json { ignoreUnknownKeys = true }
fun prettyPrintJson2(json: String) {
    println(json)
    val a: ArrayList<Temp1> = ArrayList()
    Json.parseToJsonElement(json)
        .jsonObject["result"]!!//.jsonObject["_id"]//?.json
        .jsonArray
        .forEach {
            a.add(Temp1(
                it.jsonObject["_id"]!!.jsonObject["name"].toString(),
                it.jsonObject["_id"]!!.jsonObject["course"].toString(),
                it.jsonObject["grades"]
            ).apply {
                println(
                    "Temp(name = ${this.name}, " +
                            "course = ${this.course}, " +
                            "grade = ${this.grades})"
                )
            }
            )
        }
}

fun prettyPrintJson3(json: String) {
    val data = format.decodeFromString<List<R>>(json)
    println(data)
    data.map {
        Temp2(it._id.name, it._id.course, it.grades)
            .apply {
                println("Temp(name = ${this.name}, course = ${this.course}, grade = ${this.grades})")
            }
    }
}

@Serializable
class Temp1(
    val name: String,
    val course: String,
    val grades: JsonElement?
)

@Serializable
class Temp2(
    val name: String,
    val course: String,
    val grades: Int
)

