package lab_4

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import org.bson.Document
import org.bson.conversions.Bson
import org.litote.kmongo.and
import org.litote.kmongo.*
import org.litote.kmongo.getCollection
import org.litote.kmongo.gt
import java.io.File
import kotlin.system.measureTimeMillis

// лек
@Serializable
data class Population(
    @SerialName("Country Code") val code: String,
    @SerialName("Country Name") val name: String,
    @SerialName("Value") val value: Float,
    @SerialName("Year") val year: Int
)

val population =
    mongoDatabase
        .getCollection<Population>().apply { drop() }


fun task_3() {

//    val populationJson =
//        Population::class.java.getResource("https://github.com/AltmanEA/edu-kmongo/blob/master/src/main/resources/population_json.json").readText()
//    val populationCol = Json.decodeFromString(
//        ListSerializer(Population.serializer()), populationJson)
    val populationJson =
        File(
            "C:\\Users\\uruki\\IdeaProjects" +
                    "\\KsushaYurukina\\src\\main\\resources" +
                    "\\population_json.json"
        ).readText()
    val populationCol = Json.decodeFromString(
        ListSerializer(Population.serializer()), populationJson
    )
    population.insertMany(populationCol)

//    prettyPrintExplain(population.find(Population::year eq 2018))

    val bsonRequest = and(
        Population::value gt 1e+6F,
        Population::year eq 2018
    )
    prettyPrintExplain(population.find(bsonRequest))


    /* createIndex
    Создайте индекс с заданными ключами.
    Параметры: ключи – объект, описывающий индексный ключ(ы), который не может быть нулевым.
    Возвращает: имя индекса
    */

    val yearIndex = population
        .createIndex(Document.parse("{'Year': 1}"))
    prettyPrintExplain(population.find(bsonRequest))


    population.dropIndex(yearIndex)
    prettyPrintExplain(population.find(bsonRequest))

    val yearAndValueIndex = population
        .createIndex(Document.parse("{'Year': 1, 'Value' : 1}"))
    prettyPrintExplain(population.find(bsonRequest))

//    population.dropIndex(yearAndValueIndex)

//    prettyPrintExplain(population.find(bsonRequest))

//    var i = 0
//
//    println("-".repeat(90))
//    population.find("{'Year': 2018}").forEach {
//        println("${i++}) $it")
//    }
//    println("-".repeat(90))
//    i = 0
//    population.find(Population::year eq 2018).forEach {
//        println("${i++}) $it")
//    }
//    println("-".repeat(90))
//    i = 0
//    population.find(and(Population::value gt 1e+6F, Population::year eq 2018)).forEach {
//        println("${i++}) $it")
//    }
//
//    val yearIndex = population.createIndex(Document.parse("{'Year': 2018}"))
//    population.dropIndex(yearIndex)
//    println("-".repeat(90))
//    i = 0
//    population.find("{'Year': 2018}").forEach {
//        println("${i++}) $it")
//    }
//    println("-")


//    var bsonRequest: Bson
//    val withoutIndexes = measureTimeMillis {
//        bsonRequest = and(Population::year eq 2018, Population::value gt 1_000_000_000F)
//        t.prettyPrintExplain(population.find(bsonRequest))
//    }
//    println("Without index time is - $withoutIndexes")
//    println("Индекс год ")

//    println("\n --- Find code and range years with year index ---\n")
//    val bsonRequest = and(Population::value gt (1_000_000_000).toFloat(), Population::year gt 2018)
////    prettyPrintCursor(population.find(bsonRequest))
//    prettyPrintExplain(population.find(bsonRequest))

//    println("\n --- Find code and range years without index --- \n")
//    population.dropIndex(yearIndex)
//    prettyPrintExplain(population.find(bsonRequest))
//
//    println("\n --- Find code and range years with code/year index --- \n")
//    val codeYearIndex = population.createIndex(Document.parse("{'Country Code' : 1, 'Year' : 1}"))
//    prettyPrintExplain(population.find(bsonRequest))


//    mPopulations.insertMany(populationCol)
//
//    val filter = and(Population::year eq 2018, Population::value gt 1_000_000_000.toFloat())
//    prettyPrintExplain(population.find(filter))
//    population.createIndex(Document.parse("{'Year' : 1}"))
//    prettyPrintExplain(population.find(filter))
//    population.createIndex(Document.parse("{'Value' : 1}"))
//    prettyPrintExplain(population.find(filter))
//    population.createIndex(Document.parse("{'Year' : 1}, {'Value' : 1}"))
//    prettyPrintExplain(population.find(filter))


}