//import java.util.*
//
//class Student(val name: String) {
//    override fun toString() = name
//}
//
//class Grade(val student: Student, val value: Int) {
//    override fun toString() = "$student - $value"
//}
//
//fun <T> Iterable<T>.countBy(value: Int, selector: (T) -> Int): Int {
//    if (this is Collection && isEmpty()) return 0
//    var count = 0
//    for (element in this) if (selector(element) == value) ++count
//    return count
//}
//
//fun main() {
//
//    // 1. Операции с коллекциями
//    val students = listOf(
//        Student("Sheldon"),
//        Student("Leonard"),
//        Student("Howard"),
//        Student("Raj"),
//        Student("Penny"),
//        Student("Amy"),
//        Student("Bernadette")
//    )
//
//    val studentIndexes = students.mapIndexed { index, _ -> index }
//    val studentWithIndexes = studentIndexes.zip(students)
//    val studentsString = studentWithIndexes.joinToString { "${it.first} - ${it.second}" }
//
//    // 2. Виды коллекций
//    val grades = students.map { Grade(it, (2..5).random()) }
//    val gradesSet = grades.mapTo(HashSet()) { it.value }
//    val gradesMap = grades.associate { grade -> grade.value to grades.countBy(grade.value) { it.value } }
//    val gradesMapWith = grades.associateWith { grade -> mapOf(grade.value to grades.countBy(grade.value) { it.value }) }
//
//    // 3. Реализация коллекций
//    val dequeList: Deque<Student> = LinkedList()
//    val treeMap = TreeMap<Int, Student>()
//    dequeList.addAll(LinkedList(students.subList(0, 3)))
//
//    grades.forEach {
//        treeMap.merge(it.value, it.student) { _, student -> Student(student.name) }
//    }
//
//    // Вывод
//    println("1. Операции с коллекциями")
//    println(studentIndexes)
//    println(studentWithIndexes)
//    println(studentsString)
//    println("\n")
//    println("2. Виды коллекций")
//    println(gradesMap)
//    println(gradesSet)
//    println(gradesMapWith.values.toSet())
//    println("\n")
//
//    println("3. Реализация коллекций")
//    println("Before add operation")
//    println(dequeList)
//    dequeList.addFirst(students[4])
//    println("After add operation")
//    println(dequeList)
//    println("\n")
//    println("Before get operation")
//    println(dequeList)
//    println(dequeList.first())
//    println("After get operation")
//    println(dequeList)
//    println("\n")
//    println(treeMap)
//}