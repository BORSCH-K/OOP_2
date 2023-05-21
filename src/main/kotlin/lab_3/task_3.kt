package lab_3

import java.io.FileInputStream
import javax.xml.namespace.QName
import javax.xml.stream.XMLInputFactory


fun task_3() {

    println("Задание 3")
    println("Пункт 1")
    val reader = XMLInputFactory.newInstance().createXMLEventReader(FileInputStream("src/main/resources/data.xml"))

    var nameCity = ""
    var value = ""

    while (reader.hasNext()) {
        val xmlEvent = reader.nextEvent()
        if (xmlEvent.isStartElement) {
            val startElement = xmlEvent.asStartElement()
            when (startElement.name.localPart) {
                "Description" -> {
                    if (startElement.getAttributeByName(QName("Id"))?.value == null) {
                        nameCity = reader.elementText
                    }
                }

                "Code" -> value = startElement.getAttributeByName(QName("value"))?.value.toString()

            }
        } else if (xmlEvent.isEndElement && xmlEvent.asEndElement().name.localPart == "Code"
            && (nameCity == "Омск" || nameCity == "Омская область" || nameCity == "Российская Федерация" || "Москва" in nameCity)
        ) {
            println("$nameCity $value")
        }
    }

    println("Пункт 2")

    val reader2 = XMLInputFactory.newInstance().createXMLEventReader(FileInputStream("src/main/resources/data.xml"))

    val monthAndYear = ArrayList<String>()
    val costRF = ArrayList<String>() // 643
    val costMoscow = ArrayList<String>() // 52401000000
    val costOmsk = ArrayList<String>() // 45000000000

    var k_dormitory = 0
    var cost = ""
    var city = ""

    var year = ""
    var month = ""

    while (reader2.hasNext()) {
        val xmlEvent = reader2.nextEvent()
        if (xmlEvent.isStartElement) {
            val startElement = xmlEvent.asStartElement()
            when (startElement.name.localPart) {
                "Value" -> {
                    when (startElement.getAttributeByName(QName("concept"))?.value) {
                        "s_grtov" -> k_dormitory =
                            if (startElement.getAttributeByName(QName("value"))?.value == "9415") 1 else 0

                        "s_OKATO" -> city = startElement.getAttributeByName(QName("value"))?.value!!
                        "PERIOD" -> month = startElement.getAttributeByName(QName("value"))?.value!!
                    }
                }

                "Time" -> year = reader2.elementText

                "ObsValue" -> cost = startElement.getAttributeByName(QName("value"))?.value!!
            }


        } else if (xmlEvent.isEndElement && xmlEvent.asEndElement().name.localPart == "Series"
            && k_dormitory == 1 && (year > "2012")
        ) {

            when (city) {
                "643" -> costRF.add(cost)
                "52401000000" -> costMoscow.add(cost)
                "45000000000" -> costOmsk.add(cost)
            }
            monthAndYear.add("$month $year")

        }
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




