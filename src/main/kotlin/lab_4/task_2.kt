package lab_4

import com.mongodb.client.result.UpdateResult

import org.litote.kmongo.*


val mStudents = mongoDatabase.getCollection<Student>().apply { drop() }
val mCourses = mongoDatabase.getCollection<Course>().apply { drop() }

fun task_2() {

    println("Пункт 1")
    fillStudentsAndCourse() // создает список студентов
    setGrade("Math", "Penny", 5)
    setGrade("Math", "Sheldon", 6)
    prettyPrintCursor(mCourses.find(Course::name eq "Math"))
    incGrade("Math", "Sheldon")
    prettyPrintCursor(mCourses.find(Course::name eq "Math"))
    setGrade("Math", "Raj", 0) // с ней работает, без - нет
    incGrade("Math", "Raj") // нет оценки
    prettyPrintCursor(mCourses.find(Course::name eq "Math"))

    println("Пункт 2")

    addStudent("Stuart", "Boys", listOf("Math", "Phys"))
    addStudent(
        "Emily", "Girls", listOf(
            "Math", "Chem"  // на химию не записан, так как ее нет в списках
        )
    )
    prettyPrintCursor(mCourses.find())

    println("Пункт 3")

    setAllValue("Math", 3)                              // взять отсюда для пунтка 1
    prettyPrintCursor(mCourses.find(Course::name eq "Math"))

}

fun setAllValue(course: String, value: Int) {
    mCourses.updateOne(
        Course::name eq course,
        setValue(Course::grades.allPosOp / Grade::value, value)
    )
}


fun addStudent(name: String, group: String, courses: List<String>) {
    val student = Student(name, group)
    mCourses.updateMany(
        Course::name `in` courses,
        push(Course::grades, Grade(student.id, name)))
    mStudents.insertOne(student)
}

fun incGrade(course: String, name: String) {
    mCourses.updateOne(
        and(
            Course::name eq course,
            Course::grades / Grade::studentName eq name
        ),
        inc(Course::grades.posOp / Grade::value, 1) // увел. на 1
    )
}

// из лек
fun setGrade(courseName: String, studentName: String, value: Int = 0): UpdateResult =
    mCourses.updateOne(
        and(
            Course::name eq courseName,
            Course::grades / Grade::studentName eq studentName
        ),
        setValue(Course::grades.posOp / Grade::value, value)
    )

// из лек
// заполнение студентов и курсов
fun fillStudentsAndCourse(fillCourse: Boolean = true): List<Student> {
    val students = listOf("Penny", "Amy").map { Student(it, "Girls") } +
            listOf("Sheldon", "Leonard", "Howard", "Raj")
                .map { Student(it, "Boys") }
    mStudents.insertMany(students)
    if (fillCourse) {
        val courses = listOf("Math", "Phys", "History").map {
            Course(it, students.map { Grade(it.id, it.name) })
        }
        mCourses.insertMany(courses)
    }
    return students
}

