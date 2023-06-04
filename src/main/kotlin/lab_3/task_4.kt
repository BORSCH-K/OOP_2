package lab_3

import java.io.FileInputStream
import javax.xml.stream.XMLInputFactory
import javax.xml.namespace.QName

enum class Status {
    START, MONTH, YEAR, CITY, COST, HOSTEL, CODE, NAME
}

fun task_4() {

    println("Задание 4")
    println("Пункт 1")
    val reader1 = XMLInputFactory.newInstance().createXMLEventReader(FileInputStream("src/main/resources/data.xml"))
    var nameCity = ""
    var code = ""
    var state: Status
    while (reader1.hasNext()) {
        val xmlEvent = reader1.nextEvent()
        if (xmlEvent.isStartElement) {
            val startElement = xmlEvent.asStartElement()
            state = when (startElement.name.localPart) {
                "Description" -> Status.NAME
                "Code" -> Status.CODE
                else -> Status.START
            }
            when (state) {
                Status.NAME -> {
                    if (startElement.getAttributeByName(QName("Id"))?.value == null) {
                        nameCity = reader1.elementText
                    }
                }
                Status.CODE -> code = startElement.getAttributeByName(QName("value"))?.value!!
                else -> {}
            }
        } else if (xmlEvent.isEndElement && xmlEvent.asEndElement().name.localPart == "Code"
            && (nameCity == "Омск" || nameCity == "Омская область" || nameCity == "Российская Федерация" || "Москва" in nameCity)
        ) {
            println("$nameCity $code")
        }
    }


    println("Пункт 2")

    val reader2 = XMLInputFactory.newInstance().createXMLEventReader(
        FileInputStream("src/main/resources/data.xml")
    )
    val monthAndYear = ArrayList<String>()
    val costRF = ArrayList<String>() // 643
    val costMoscow = ArrayList<String>() // 52401000000
    val costOmsk = ArrayList<String>() // 45000000000
    var k_dormitory = 0
    var cost = ""
    var city = ""
    var year = ""
    var month = ""
    state = Status.START

    while (reader2.hasNext()) {
        val xmlEvent = reader2.nextEvent()
        if (xmlEvent.isStartElement) {
            val startElement = xmlEvent.asStartElement()
            state = when (startElement.name.localPart) {
                "Time" -> Status.YEAR
                "ObsValue" -> Status.COST
                "Value" -> {
                    when (startElement.getAttributeByName(QName("concept"))?.value) {
                        "s_OKATO" -> Status.CITY
                        "s_grtov" -> Status.HOSTEL
                        "PERIOD" -> Status.MONTH
                        else -> state }}
                else -> Status.START}
            when (state) {
                Status.YEAR -> year = reader2.elementText
                Status.COST -> cost = startElement.getAttributeByName(QName("value"))?.value!!
                Status.CITY -> city = startElement.getAttributeByName(QName("value"))?.value!!
                Status.HOSTEL -> k_dormitory =
                    if (startElement.getAttributeByName(QName("value"))?.value == "9415") 1 else 0
                Status.MONTH -> month = startElement.getAttributeByName(QName("value"))?.value!!
                else -> {}}
        } else if (xmlEvent.isEndElement && xmlEvent.asEndElement().name.localPart == "Series"
            && k_dormitory == 1 && (year > "2012")
        ) {
            when (city) {
                "643" -> costRF.add(cost)
                "52401000000" -> costMoscow.add(cost)
                "45000000000" -> costOmsk.add(cost) }
            monthAndYear.add("$month $year") }
    }

    println("|     ПЕРИОД     |   РФ   |  Москва |  Омск   |")
    for (i in 0 until costOmsk.size) {
        println(
            "| %-14s | %-6s | %-7s | %-7s |".format( // Форматированный вывод
                monthAndYear[i], costRF[i], costMoscow[i], costOmsk[i]
            )
        )
    }
    println()
}