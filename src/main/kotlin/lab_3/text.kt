package lab_3

val text = """
val boys: List<String> = listOf("Себастьян", "Акакий", "Ашот", "Кузьма")
val girls: List<String> = listOf("Жанна", "Агата", "Забава", "Барбара", "Фрида")
val students: List<Student> = (boys + girls).map { Student(it) }
fun <T> Iterable<T>.countBy(value: Int, selector: (T) -> Int): Int {
    if (this is Collection && isEmpty()) return 0
    var count = 0
    for (element in this)
        if (selector(element) == value)
            count++
    return count
}
val a = 0
val aasd = 1
fun main() {
    +students
    val studentIndexes = students.mapIndexed { index, _ -> index }
    +studentIndexes
    val studentWithIndexes = studentIndexes.zip(students)
    +studentWithIndexes
    val studentsString = studentWithIndexes.joinToString(prefix = "", postfix = "") {
        listOf(
            it.first.toString(),
            it.second.name
        ).joinToString(prefix = "", postfix = "", separator = " - ")
    }
    +studentsString
    val list1 = listOf(0, 1, 25, 3, 5, 6, 32, 5, 2, 1, 4, 1, 1, 1, 0)
    val c0 = list1.countBy(1) { l -> l }
    println(c0)
    val grades = students.map { Grade(it.name, (3..5).random()) }
    +grades
    val gradesSet = grades.mapTo(hashSetOf()) { it.num }
    +gradesSet
    val gradesMap = gradesSet.associate { i -> i to grades.countBy(i) { it.num } }
    +gradesMap
    val gradesMapWith = gradesSet.associateWith { i -> grades.countBy(i) { it.num } }
    +gradesMapWith
    val students_0 = boys + girls
    val deque: Deque<String> = LinkedList(students_0.subList(0, 3))
    +deque
    +deque.addFirst(students_0[4])
    +deque
    +deque.first
    val mergeTreeMap = TreeMap<Int, String>()
    val m = listOf("Себастьян" to 3, "Кузьма" to 5, "Жанна" to 4, "Фрида" to 3, "Забава" to 4, "Акакий" to 3)
    m.map {
        mergeTreeMap.merge(it.second, it.first) { i, j -> " $ i, $ j" }
    }
    +mergeTreeMap
    val nameSizeMap = students_0.associateWith { i -> i.length }
    +nameSizeMap
}
"""