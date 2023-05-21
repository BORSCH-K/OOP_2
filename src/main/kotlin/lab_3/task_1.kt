package lab_3

fun task_1() {
    println("Задание 1\n")
    val splitExample = text

    listOf(
        Regex("va[rl] [a-zA-Z0-9]+"),
        Regex("va[rl] [a-z][a-zA-Z0-9]*"),
        Regex("va[lr] (\\w+)(?![\\w\\W]*\\1)")
    ).forEach { regex ->
        println("---pattern: ${regex.pattern}---")
        regex.findAll(splitExample).toList().forEach { it ->
            it.groups.forEach {
                println(it!!.value)
            }
        }
    }
    println()
}