package lab_4

import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import org.bson.Document
import org.litote.kmongo.and
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection
import org.litote.kmongo.gt
import java.io.File

val mPopulations = mongoDatabase.getCollection<Population>().apply { drop() }

fun task_3(){

    val populationJson = File("C:\\Users\\uruki\\IdeaProjects\\KsushaYurukina\\src\\main\\resources\\population_json.json").readText()
    val populationCol = Json.decodeFromString(
        ListSerializer(Population.serializer()), populationJson
    )
    mPopulations.insertMany(populationCol)

    val filter = and(Population::year eq 2018, Population::value gt 1_000_000_000.toFloat())
    prettyPrintExplain(mPopulations.find(filter))
    mPopulations.createIndex(Document.parse("{'Year' : 1}"))
    prettyPrintExplain(mPopulations.find(filter))
    mPopulations.createIndex(Document.parse("{'Value' : 1}"))
    prettyPrintExplain(mPopulations.find(filter))
    mPopulations.createIndex(Document.parse("{'Year' : 1}, {'Value' : 1}"))
    prettyPrintExplain(mPopulations.find(filter))




}