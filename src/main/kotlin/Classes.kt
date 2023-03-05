class Student(val name: String) {
    override fun toString() = name
}

class Grade(val name: String, val num: Int) {
    override fun toString(): String {
        return "$name: $num"
    }
}