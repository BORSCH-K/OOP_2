package lab_3

import org.w3c.dom.Node
import org.w3c.dom.NodeList
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory
import javax.xml.parsers.DocumentBuilderFactory


// https://regex101.com/ - для построения регулярных выражений
private val parser =
        XPathFactory
                .newInstance()
                .newXPath()

fun main() {
    println("--- ПУНКТ 1 ---\n")
    val splitExample = text
//va[rl] [a-zA-Z0-9]+
//va[rl] [a-z][a-zA-Z0-9]*
//va[lr] (\w+)(?![\w\W]*\1)

//    val regex = Regex("va[rl]\\s+[A-Za-z]+[A-Za-z]") // (val|var) [a-zA-Z0-9]+

//    val result = splitExample.split(regex)
//    val match = regex.findAll(splitExample).toList()

    val regex = listOf(
            Regex("va[rl] [a-zA-Z0-9]+"),
            Regex("va[rl] [a-z][a-zA-Z0-9]*"),
            Regex("va[lr] (\\w+)(?![\\w\\W]*\\1)")  // RegexOption.DOT_MATCHES_ALL???????
    ).forEach { regex ->
        println("---pattern: ${regex.pattern}---")
        regex.findAll(splitExample).toList().forEach { it ->
            it.groups.forEach {
                println(it!!.value)
            }

        }


    }
//    match.forEach{ it ->
//        it.groups.forEach {
//            println(it!!.value)
//        }
//    }

    println("\n--- ПУНКТ 2 ---\n")

    val parser = XPathFactory.newInstance().newXPath()
    val doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("src/main/resources/data.xml")

    // 1ый подпункт
    val cursorQuery = { node: Node ->
        node.firstChild.nodeValue + " - " + node.parentNode.attributes.getNamedItem("value").nodeValue
    }
    val query = listOf(
            "//CodeList[@id='s_OKATO']/Code/Description[text()='Российская Федерация']" to cursorQuery,
            "//CodeList[@id='s_OKATO']/Code/Description[text()='Москва']" to cursorQuery,
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

    println()
    // 2ой подпункт
    listOf(
            "//CodeList[@id='s_grtov']/Code/Description" to { node: Node ->
                node.parentNode.attributes.getNamedItem("value").nodeValue + " - " + node.firstChild.nodeValue + "\n"
            }
    ).map { (query, cursorQuery) ->
        val result = parser
                .compile(query)
                .evaluate(doc, XPathConstants.NODESET) as NodeList
        result.map(cursorQuery).also {
            print(it)
        }
        println()
    }


}


fun <R> NodeList.map(transform: (Node) -> R): List<R> =
        ArrayList<R>().also {
            for (index in 0 until length)
                it.add(transform(item(index)))
        }

fun Node.getText(query: String) = parser.compile(query).evaluate(this).toString()