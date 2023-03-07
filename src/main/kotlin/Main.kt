import java.util.*

operator fun Any?.unaryPlus() =
    this.apply { println(this) }


val boys: List<String> = listOf("Себастьян", "Акакий", "Ашот", "Кузьма")
val girls: List<String> = listOf("Жанна", "Агата", "Забава", "Барбара", "Фрида")

val students: List<Student> = (boys + girls).map { Student(it) }

// count - счет/ Возвращает количество элементов, соответствующих заданному предикату.
// maxBy / maxByOrNull - возмращает максимальное число или null

// считает количество элементов, удовлетворяющих условию selector (в данном случае подсчитывает количество повторений элемента)
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
    +students

    // записывает индексы
    val studentIndexes = students.mapIndexed { index, _ -> index }
    // index, _ (могут быть любыми буквами)
    // index - индекс элемента
    // _ - сам элемент students с индексом index (в данном случае он пустой)
    +studentIndexes

    // Возвращает список пар, построенных из элементов этого массива и другого массива с тем же индексом.
    // Возвращаемый список имеет длину самой короткой коллекции.
    val studentWithIndexes = studentIndexes.zip(students)
    +studentWithIndexes

    //val studentsString = studentWithIndexes.map { listOf(it.first.toString(), it.second.name).joinToString(prefix = "", postfix = "", separator = " - ") }.joinToString(prefix = "", postfix = "")
    // Создает строку из всех элементов, разделенных с помощью разделителя и использующих заданный префикс и постфикс, если они указаны.
    val studentsString = studentWithIndexes.joinToString(prefix = "", postfix = "") {
        listOf(
            it.first.toString(),
            it.second.name
        ).joinToString(prefix = "", postfix = "", separator = " - ")
    }
    +studentsString // 0 - Себастьян, 1 - Акакий, 2 - Ашот, 3 - Кузьма, 4 - Жанна, 5 - Агата, 6 - Забава, 7 - Барбара, 8 - Фрида

    // 2 ПУНКТ

    val list1 = listOf(0, 1, 25, 3, 5, 6, 32, 5, 2, 1, 4, 1, 1, 1, 0)
//    +list1
    val c0 = list1.countBy(1) { l -> l }
    println(c0) // 5

    val grades = students.map { Grade(it.name, (3..5).random()) }
    +grades // Имя: оценка

    /* Set - Общая неупорядоченная коллекция элементов, которая не поддерживает повторяющиеся элементы.
    Методы в этом интерфейсе поддерживают доступ к набору только для чтения;
    доступ на чтение/запись поддерживается через интерфейс MutableSet.*/

    // список видов оценок
    // HashSet представляет хеш-таблицу
    val gradesSet = grades.mapTo(hashSetOf()) { it.num }
    +gradesSet

    /* Map - Коллекция, которая содержит пары объектов (ключи и значения) и
    поддерживает эффективное извлечение значения, соответствующего каждому ключу.
    Ключи карты уникальны; карта содержит только одно значение для каждого ключа*/

    // Возвращает карту, содержащую пары ключ-значение, предоставленные функцией преобразования, примененной к элементам данной последовательности.
    // пары: оценка, количество
    val gradesMap = gradesSet.associate { i -> i to grades.countBy(i) { it.num } }
    +gradesMap

    // то же, что и associate, только не пребует ключ (оценку)
    val gradesMapWith = gradesSet.associateWith { i -> grades.countBy(i) { it.num } }
    +gradesMapWith

    // 3 ПУНКТ
    // LIFO - last in, first out

    val students_0 = boys + girls // просто String

    // Deque - это двусторонняя очередь, которая позволяет добавлять или удалять элементы с обоих концов (заднего и переднего).
    val deque: Deque<String> = LinkedList(students_0.subList(0, 3))

    // LinkedList — класс, реализующий два интерфейса — List и Deque.
    // Это обеспечивает возможность создания двунаправленной очереди из любых (в том числе и null) элементов.

    +deque
    +deque.addFirst(students_0[4])
    +deque
    +deque.first

    /** getFirst(): возвращает элемент в начало списка
     * getLast(): возвращает элемент в конце списка
     * addFirst(): добавляет элемент в начало списка
     * addLast(): добавляет элемент в конец списка
     * removeFirst(): удаляет элемент в начале списка
     * removeLast(): удаляет элемент в конце списка */

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
    // TreeMap обеспечивает эффективное средство хранения пар ключ/значение в отсортированном порядке и позволяет быстро извлекать данные. // Java

    val m = listOf("Себастьян" to 3, "Кузьма" to 5, "Жанна" to 4, "Фрида" to 3, "Забава" to 4, "Акакий" to 3)

    // to создает кортеж типа Pair

    m.map {
        // Объединяет заданные потоки в единый поток без сохранения порядка элементов.
        mergeTreeMap.merge(it.second, it.first) { i, j -> " $i, $j" }
    }
    +mergeTreeMap

    // Защита
    val nameSizeMap =  students_0.associateWith { i -> i.length }
    +nameSizeMap


}