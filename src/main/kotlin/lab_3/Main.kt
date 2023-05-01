package lab_3

// https://regex101.com/ - для построения регулярных выражений

fun main() {
    println("--- ПУНКТ 1 ---\n")
    val splitExample = text


    val regex = Regex("va[rl] [a-zA-Z0-9]+") // (val|var) [a-zA-Z0-9]+

    val result = splitExample.split(regex)
    val match = regex.findAll(splitExample).toList()

    match.forEach{
        it.groups.forEach {
            println(it!!.value)
        }
    }

//    result.zip(match).forEach {
//        it.second.groups.forEach { matchGroup ->
//            println(matchGroup?.value)
//        }
//    }

//    val regex1 = Regex("val\\s+(\\w+)(?![\\s\\S]*\\b\\1).*") // (val|var) [a-zA-Z0-9]+va[rl] [a-zA-Z0-9]+
//
//    val result1 = splitExample.split(regex1)
//    val match1 = regex.findAll(splitExample).toList()
//
//    result1.zip(match1).forEach {
//        it.second.groups.forEach { matchGroup ->
//            println(matchGroup?.value)
//        }
//    }





}

//    println("\n--- Replace example ---\n")
//    val replaceExample = "Федор Михайлович Достоевский и Лев Николаевич Толстой. И Александр Сергеевич Пушкин, конечно."
//    val replaceRegex = Regex("([А-Я][а-я]+ ){2}[А-Я][а-я]+") // Диапазоны, квантификаторы
//    println(replaceExample.replace(replaceRegex) {
//        val fio = it.value.split(" ")
//        "${fio[2]} ${fio[0][0]}. ${fio[1][0]}."
//    })
//
//    println("\n--- Find example ---\n")
//    val findExample = "<div class='x'> text <b> bold </b> text </div>"
//    listOf(
//        Regex("<.*>"),                      // Точка, квантификатор *
//        Regex("<[^>]*>"),                   // Отрицание
//        Regex("<.*?>"),                     // Ленивая квантификация
//        Regex("<(.*?)>.*</\\1>"),           // Группы
//        Regex("<(\\w*?)(.*?)>(.*)</\\1>"),  // Классы символов
//    ).forEach { regex ->
//        println("--- Pattern: ${regex.pattern} --- ")
//        regex.findAll(findExample).forEach {
//            print("---")
//            it.groups.forEach { matchGroup ->
//                println("\t range - ${matchGroup?.range}; value - |${matchGroup?.value}|")
//            }
//        }
//    }
//}