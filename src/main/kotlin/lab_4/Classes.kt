package lab_4

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.util.*

// 1
@Serializable
data class Count(val name: String, val value: Int = 0)

// 2
@Serializable
data class Student(
    val name: String,
    val group: String,
    @Contextual val id: Id<Student> = newId()
)

@Serializable
data class Grade(
    val studentId: @Contextual Id<Student>,
    val studentName: String,
    val value: Int? = null,
    @Serializable(with = DateAsLongSerializer::class)
    val date: Date? = null,
)

@Serializable
data class Course(
    val name: String,
    val grades: List<Grade> = emptyList() // пустой список
)

@Serializable
data class Population(
    @SerialName("Country Code") val code: String,
    @SerialName("Country Name") val name: String,
    @SerialName("Value") val value: Float,
    @SerialName("Year") val year: Int
)

@Serializable
data class Grade2 (
    @Contextual val courseId: Id<Course2>? = null, // id дисциплины (предмета)
    val courceName: String? = null,
    val value: Int = 0, // Оценка студента
    @Serializable(with = dop.DateAsLongSerializer::class)
    val date: Date? = null // Дата получения оценки
)

@Serializable
data class Course2(
    @Contextual val courceId: Id<Course2> = newId(), // id дисциплины (предмета)
    val name: String, // Название дисциплины (предмета)
)

@Serializable
data class Student2(
    val name: String, // Имя студента
    val group: String, // Группа студента (Boys, Girls)
    val grades: List<Grade2> = emptyList() // Коллекция оценок
//    @Contextual val id: Id<Student2> = newId() // id студента
)

@Serializable
class UnwindStudents2(
    val name: String,
    val grades: Grade2
)

@Serializable
class UnwindStudentsCourses2(
    val name: String,
    val course: String,
    val value: Int
)

@Serializable
data class Id(
    val name: String,
    val course: String,
)

@Serializable
data class Result(
    val _id: lab_4.Id,
    val grades: Int
)

@Serializable
data class DefId(
    val name: String,
//    val cources: List<String>
)

@Serializable
data class Def(
    val name: String,
    val courses: String,
    val sumGrades: Int
)

@Serializable
data class DefResult(
    val _id: DefId,
    val courses: List<String>,
    val sumGrades: Int,
)