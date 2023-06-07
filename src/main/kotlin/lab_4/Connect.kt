package lab_4

import com.mongodb.ExplainVerbosity
import com.mongodb.client.FindIterable
import org.json.JSONObject
import org.litote.kmongo.KMongo
import org.litote.kmongo.formatJson
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

fun prettyPrintCursor2(cursor: Iterable<R>) =
//    println(cursor.json)
    prettyPrintJson2(cursor.json)

//    cursor.forEach {
//        println(it)
////        println(it._id.name + " "+ it._id.course + " " + it.grades)
////println(it)
////        prettyPrintJson("{ result: ${it._id.name.json}")
//    }
fun prettyPrintJson2(json: String) = // оформление
    println(JSONObject(json).toString(4))