import java.util.*

operator fun Any?.unaryPlus() =
        this.apply { println(this) }


val boys: List<String> = listOf("Себастьян", "Акакий", "Ашот", "Кузьма")
val girls: List<String> = listOf("Жанна", "Агата", "Забава", "Барбара", "Фрида")

val students: List<Student> = (boys + girls).map { Student(it) }

// count - счет/ Возвращает количество элементов, соответствующих заданному предикату.
// maxBy / maxByOrNull - возмращает максимальное число или null
fun <T> Iterable<T>.countBy(value: Int, selector: (T) -> Int): Int {
    if (this is Collection && isEmpty()) return 0
    var count = 0
    for (element in this)
        if (selector(element) == value)
            count++
    return count
}

fun main() {
    // 1 ПУНКТ
    +students // [Себастьян, Акакий, Ашот, Кузьма, Жанна, Агата, Забава, Барбара, Фрида]
    val studentIndexes = students.mapIndexed { index, _ -> index }
    // index, _ (могут быть любыми буквами)
    // index - индекс элемента
    // _ - сам элемент students с индексом index (в данном случае он пустой)
    +studentIndexes // [0, 1, 2, 3, 4, 5, 6, 7, 8]

    val studentWithIndexes = studentIndexes.zip(students)
    +studentWithIndexes // [(0, Себастьян), (1, Акакий), (2, Ашот), (3, Кузьма), (4, Жанна), (5, Агата), (6, Забава), (7, Барбара), (8, Фрида)]

    //val studentsString = studentWithIndexes.joinToString(prefix = "", postfix = "", separator = " ")
    //val studentsString = studentWithIndexes.map { listOf(it.first.toString(), it.second.name).joinToString(prefix = "", postfix = "", separator = " - ") }.joinToString(prefix = "", postfix = "")
    val studentsString = studentWithIndexes.joinToString(prefix = "", postfix = "") {
        listOf(
                it.first.toString(),
                it.second.name
        ).joinToString(prefix = "", postfix = "", separator = " - ")
    }
    //val s = listOf('a','d').joinToString(prefix = "", postfix = "", separator = "-")
    +studentsString // 0 - Себастьян, 1 - Акакий, 2 - Ашот, 3 - Кузьма, 4 - Жанна, 5 - Агата, 6 - Забава, 7 - Барбара, 8 - Фрида

    // 2 ПУНКТ
    val list1 = listOf(0, 1, 25, 3, 5, 6, 32, 5, 2, 1, 4, 1, 1, 1, 0)
    +list1 // [0, 1, 25, 3, 5, 6, 32, 5, 2, 1, 4, 1, 1, 1, 0]
    val c0 = list1.countBy(1) { l -> l }
    println(c0) // 5

    val grades = students.map { Grade(it.name, (3..5).random()) }
    +grades // Имя: оценка

    /* Set - Общая неупорядоченная коллекция элементов,
    которая не поддерживает повторяющиеся элементы. Методы
    в этом интерфейсе поддерживают доступ к набору только для чтения;
    доступ на чтение/запись поддерживается через интерфейс MutableSet.
     */
    val gradesSet = grades.mapTo(hashSetOf()) { it.num }
// HashSet представляет хеш-таблицу
    +gradesSet

    /* Map - Коллекция, которая содержит пары объектов (ключи и значения) и
    поддерживает эффективное извлечение значения, соответствующего каждому
    ключу. Ключи карты уникальны; карта содержит только одно значение для
    каждого ключа*/
    val gradesMap = gradesSet.associate { i -> i to grades.countBy(i) { it.num } }
// to создает кортеж типа Pair
    +gradesMap

    val gradesMapWith = gradesSet.associateWith { i -> grades.countBy(i) { it.num } }
    +gradesMapWith

    // 3 ПУНКТ

    // подумать как сделать для класса Student
    val students_0 = listOf("Себастьян", "Акакий", "Ашот", "Кузьма", "Жанна", "Агата", "Забава", "Барбара", "Фрида")
    /*Deque - это двусторонняя очередь, которая позволяет добавлять или
    удалять элементы с обоих концов (заднего и переднего).*/
// LIFO - last in, first out
    val deque: Deque<String> = LinkedList(students_0.subList(0, 3))
    /* LinkedList — класс, реализующий два интерфейса — List и Deque.
    Это обеспечивает возможность создания двунаправленной очереди из любых
    (в том числе и null) элементов.  */
//    deque.add(.toString())
    +deque
    +deque.addFirst(students_0[4])
    +deque

    +deque.first
//    deque.removeFirst()
//    +deque
    /** getFirst(): возвращает элемент в начало списка
     * getLast(): возвращает элемент в конце списка
     * addFirst(): добавляет элемент в начало списка
     * addLast(): добавляет элемент в конец списка
     * removeFirst(): удаляет элемент в начале списка
     * removeLast(): удаляет элемент в конце списка */
//    grades.forEach()

//    val mergeTreeMap = TreeMap<Int, MutableList<String>>()
//
//    +mergeTreeMap
//    for (key in gradesSet) {
//        val l : MutableList<String> = mutableListOf()
//        grades.forEach{
//            if (key == it.num) {
//                l.add(it.name)
//            }
//        }
//        mergeTreeMap.merge(key, l) {_,_-> l}
//    }
    val mergeTreeMap = TreeMap<Int, String>()

    val m = listOf("Sheldon" to 5, "Leonard" to 4, "Howard" to 4, "Raj" to 3)

    m.map {
        mergeTreeMap.merge(it.second, it.first) { i, j -> " $i, $j" }
    }
//    for (key in grades) {
//
//        mergeTreeMap.merge(key.num, key.name) { _, k -> "fj" }
//    }
    +mergeTreeMap

//    val gradesMerge = grades.merge()


}
