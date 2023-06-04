package lab_4

import com.mongodb.ExplainVerbosity
import com.mongodb.client.FindIterable
import org.json.JSONObject
import org.litote.kmongo.KMongo
import org.litote.kmongo.json

val client = KMongo // подключение к серверу
    .createClient("mongodb://root:example@127.0.0.1:27017")
val mongoDatabase = client.getDatabase("lab_4") // быстрое подключение к БД на сервере

fun prettyPrintJson(json: String) = // оформление
    println(JSONObject(json).toString(4))

fun prettyPrintCursor(cursor: Iterable<*>) =
    prettyPrintJson("{ result: ${cursor.json} }")

fun prettyPrintExplain(cursor: FindIterable<*>) =
    prettyPrintJson(cursor.explain(ExplainVerbosity.EXECUTION_STATS).json)
