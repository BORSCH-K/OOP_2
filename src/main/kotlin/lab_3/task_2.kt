package lab_3

import org.w3c.dom.Node
import org.w3c.dom.NodeList
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

val parser = XPathFactory.newInstance().newXPath()

fun <R> NodeList.map(transform: (Node) -> R): List<R> =
    ArrayList<R>().also {
        for (index in 0 until length) {
            it.add(transform(item(index)))
        }

    }

fun Node.getText(query: String) = parser.compile(query).evaluate(this).toString()

fun task_2() {

    println("Задание 2\n")

    val parser = XPathFactory.newInstance().newXPath()
    val doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("src/main/resources/data.xml")
    println("Пункт 1")
    val cursorQuery = { node: Node ->
        node.firstChild.nodeValue + " - " + node.parentNode.attributes.getNamedItem("value").nodeValue
    }
    val query = listOf(
        "//CodeList[@id='s_OKATO']/Code/Description[text()='Российская Федерация']" to cursorQuery,
        "//CodeList[@id='s_OKATO']/Code/Description[contains(.,'Москва')]" to cursorQuery,
        "//CodeList[@id='s_OKATO']/Code/Description[text()='Омск']" to cursorQuery,
        "//CodeList[@id='s_OKATO']/Code/Description[text()='Омская область']" to cursorQuery
    )
    query.map { (query, cursorQuery) ->
        val result = parser.compile(query).evaluate(doc, XPathConstants.NODESET) as NodeList
        result.map(cursorQuery).also {
            print(it)
        }
        println()
    }
    println("Пункт 2")
    listOf(
        "//CodeList[@id='s_OKATO']/Code[contains(Description, 'Москва') or contains(Description, 'Омск') " +
                "or contains(Description, 'Российская Федерация')]"
                to { node: Node ->
            node.getText("@value") + " - " + node.getText("Description")
        },
        "//CodeList[@id='s_grtov']/Code" to { node: Node ->
            node.getText("@value") + " - " + node.getText("Description")}

    ).map{(query, cursorQueryStaff) ->
        val result = parser.compile(query).evaluate(doc, XPathConstants.NODESET) as NodeList
        result.map(cursorQueryStaff).also {
            print(it)
        }
        println()
    }

    println("Пункт 3")
//    val doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("src/main/resources/data.xml")
    val month = ArrayList<String>() // месяца
    val counterRF = ArrayList<String>() // цены для Рф
    val counterMoscow = ArrayList<String>() // цены для Москвы
    val counterOmsk = ArrayList<String>() // цены для Омска
    listOf("//DataSet/Series/SeriesKey/Value[@concept='s_grtov'][@value='9415']" to
                { node: Node ->
                    if (node.getText("../..//Time") > "2012") {
                        when (node.getText("../..//SeriesKey/Value[@concept='s_OKATO']/@value")) {
                            "643" -> {
                                counterRF.add(node.getText("../..//Obs/ObsValue/@value"))
                                val monthAndYearTemp = node.getText("../..//Value[@concept='PERIOD']/@value") + " " + node.getText("../..//Time")
                                month.add(monthAndYearTemp)
                            }
                            "45000000000" -> counterMoscow.add(node.getText("../..//Obs/ObsValue/@value"))
                            "52401000000" -> counterOmsk.add(node.getText("../..//Obs/ObsValue/@value"))
                        }
                    }
                }
    ).map { (query, cursorQuery) ->
        val result = (parser.compile(query).evaluate(doc, XPathConstants.NODESET) as NodeList)
        result.map(cursorQuery)//.also {
    }
    println("|     ПЕРИОД     |   РФ   |  Москва |  Омск  |")
    for (i in 0 until month.size) {
        println(
            "| %14s | %6s | %7s | %6s |".format( // Форматированный вывод
                month[i], counterRF[i], counterMoscow[i], counterOmsk[i]
            )
        )
    }

    // таблица со стоимостью различных способов проживания, доступных для студентов в городе Омске
    // 9415 - Проживание в студенческом общежитии, месяц
    // 9417 - Наём жилых помещений в государственном и муниципальном жилищных фондах, м2 общей площади
    // 9418 - Плата за жилье в домах государственного и муниципального жилищных фондов, м2 общей площади
    // 9419 - Аренда однокомнатной квартиры у частных лиц, месяц
    // 9421 - Аренда двухкомнатной квартиры у частных лиц, месяц

    println("Пункт 4")
    val monthHomeTemp = ArrayList<String>()
    val cost9415 = ArrayList<String>()
    val cost9419 = ArrayList<String>()
    val cost9421 = ArrayList<String>()
    listOf("//DataSet/Series/SeriesKey/Value[@concept='s_OKATO'][@value='52401000000']" to
                { node: Node ->
                    if (node.getText("../..//Time") > "2012") {
                        when (node.getText("..//Value[@concept='s_grtov']/@value")) {
                            "9415" -> {
                                val monthAndYearTemp = node.getText("../..//Value[@concept='PERIOD']/@value") + " " + node.getText("../..//Time")
                                monthHomeTemp.add(monthAndYearTemp)
                                cost9415.add(node.getText("../..//Obs/ObsValue/@value"))
                            }
                            "9419" -> cost9419.add(node.getText("../..//Obs/ObsValue/@value"))
                            "9421" -> cost9421.add(node.getText("../..//Obs/ObsValue/@value"))
                        }
                    }
                    ""
                }
    ).map { (query, cursorQuery) ->
        val result = (parser.compile(query).evaluate(doc, XPathConstants.NODESET) as NodeList)
        result.map(cursorQuery)
    }
    println("\nТаблица со стоимостью различных способов проживания, доступных для студентов в городе Омске")
    println("    Месяц     |  9415  |   9419   |   9421")
    for (i in 0 until monthHomeTemp.size) {
        println("%13s | %6s | %8s | %8s".format(monthHomeTemp[i], cost9415[i], cost9419[i], cost9421[i]))
    }
    println()

}