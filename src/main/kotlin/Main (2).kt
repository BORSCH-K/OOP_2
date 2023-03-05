//operator fun Any?.unaryPlus() =
//    this.apply { println(this) }
//
//
//val boys: List<String> = listOf("Себастьян", "Акакий", "Ашот", "Кузьма")
//val girls: List<String> = listOf("Жанна", "Агата", "Забава", "Барбара", "Фрида")
//
//val students: List<Student> =
//    (boys + girls).map { Student(it) }
//
//val studentIndexes = students.mapIndexed { index, _ -> index }
//// index, _ (могут быть любыми буквами)
//// index - индекс элемента
//// _ - сам элемент students с индексом index (в данном случае он пустой)
//
//val studentWithIndexes = studentIndexes.zip(students)
//
////val studentsString = studentWithIndexes.joinToString(prefix = "", postfix = "", separator = " ")
////val studentsString = studentWithIndexes.map { listOf(it.first.toString(), it.second.name).joinToString(prefix = "", postfix = "", separator = " - ") }.joinToString(prefix = "", postfix = "")
//val studentsString = studentWithIndexes.joinToString(prefix = "", postfix = "") {
//    listOf(
//        it.first.toString(),
//        it.second.name
//    ).joinToString(prefix = "", postfix = "", separator = " - ")
//}
////val s = listOf('a','d').joinToString(prefix = "", postfix = "", separator = "-")
//
//// count - счет/ Возвращает количество элементов, соответствующих заданному предикату.
//// maxBy / maxByOrNull - возмращает максимальное число или null
//val list1 = listOf(0, 1, 25, 3, 5, 6, 32, 5, 2, 1, 4, 1, 1, 1, 0)
//fun <T> Iterable<T>.countBy(value: Int, selector: (T) -> Int): Int {
//    if (this is Collection && isEmpty()) return 0
//    var count = 0
//    for (element in this)
//        if (selector(element) == value)
//            count++
//    return count
//}
//
//class Grade(val name: String, val num: Int) {
//    override fun toString(): String {
//        return "$name: $num"
//    }
//}
//
//val grades = students.map { Grade(it.name, (2..5).random()) }
//
//fun main() {
//    // 1 пункт
//    +students               // [Себастьян, Акакий, Ашот, Кузьма, Жанна, Агата, Забава, Барбара, Фрида]
//    +studentIndexes         // [0, 1, 2, 3, 4, 5, 6, 7, 8]
//    +studentWithIndexes     // [(0, Себастьян), (1, Акакий), (2, Ашот), (3, Кузьма), (4, Жанна), (5, Агата), (6, Забава), (7, Барбара), (8, Фрида)]
//    +studentsString         // 0 - Себастьян, 1 - Акакий, 2 - Ашот, 3 - Кузьма, 4 - Жанна, 5 - Агата, 6 - Забава, 7 - Барбара, 8 - Фрида
//
//    // 2 пункт
//    +list1                  // [0, 1, 25, 3, 5, 6, 32, 5, 2, 1, 4, 1, 1, 1, 0]
//    val c0 = list1.countBy(1) { l -> l }
//    println(c0)             // 5
//
//    +grades
//
//
//    // 3 пункт
//
//}