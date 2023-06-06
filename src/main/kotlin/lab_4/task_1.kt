package lab_4

import com.mongodb.client.model.*
import org.litote.kmongo.getCollection
import org.litote.kmongo.*


val countsMongo = mongoDatabase.getCollection<Count>().apply { drop() }
// getCollection - Получает коллекцию, Возвращает коллекцию
// drop() - Удаляет эту коллекцию из базы данных.

fun incCount(countName: String) {
    countsMongo.updateOne(
        Count::name eq countName,     // ищет в бд нужный эдеиент
        inc(Count::value, 1)        // что? на сколько?
    )
}

fun incOrCreateCount(countName: String) {
    countsMongo.updateOne(
        Count::name eq countName,
        inc(Count::value, 1),
        UpdateOptions().upsert(true)            // UpdateOptions() - Параметры, которые следует применять при обновлении документов.
    )                                           // upsert() установает значение true, если необходимо вставить новый документ, если нет совпадений с фильтром запроса.
}

fun task_1() {

    println("Пункт 1")
    val count = listOf("Tables" to 0, "Figures" to 0, "Equations" to 0)
//    var count = countStart.map { Count(it.first, it.second) }

    // создание БД
    countsMongo.insertMany(count.map { Count(it.first, it.second) })

    incCount("Tables")      // 1
    incCount("Tables")      // 2
    incCount("Equations")   // 1
    incCount("Listings")    // не существует - ничего не произойдет
    prettyPrintCursor(countsMongo.find())

    println("Пункт 2")
    incOrCreateCount("Tables")   // 3
    incOrCreateCount("Listings") // создается с 1
    incOrCreateCount("Listings") // 2
    prettyPrintCursor(countsMongo.find())

    println("Пункт 3")

    val more0 = countsMongo.find(Count::value gt 0)
    val range = countsMongo.find(
        and(Count::value gte 1, Count::value lte 2)
    )
    println("Больше 0:")
    prettyPrintCursor(more0)
    println("От 1 до 2:")
    prettyPrintCursor(range)

}