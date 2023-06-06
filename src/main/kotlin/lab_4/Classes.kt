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
data class Course(
    val name: String,
    val grades: List<Grade> = emptyList() // пустой список
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
data class Student(
    val name: String,
    val group: String,
    @Contextual val id: Id<Student> = newId()
)


// 4 ----------------------------------
@Serializable
class UnwindStudents(
    val name: String,
    val grades: GradeNew
)

@Serializable
class CourseAndGrade(
    val nameCourse: String,
    val grade: Int
)

@Serializable
class UnwindStudentsCourses(
    val name: String,
    val courseAndGrade : CourseAndGrade,
//    val course: String,
//    val value: Int
)



// 4 ----------------------------------------------------------------------------
@Serializable
data class StudentNew(
    val name: String, // Имя студента
    val group: String, // Группа студента (Boys, Girls)
    var grades: List<GradeNew> = emptyList(), // Коллекция оценок
//    val studentId: @Contextual Id<StudentNew> = newId(), // id студента
//    val course: List<CourseNew> = emptyList()
)

@Serializable
data class GradeNew(
//    val studentId: @Contextual Id<StudentNew>,
//    @Contextual val courseId: org.litote.kmongo.Id<Course2>? = null, // id дисциплины (предмета)
    val courseName: String, //? = null,
//    val value: Int = 0, // Оценка студента
//    @Serializable(with = DateAsLongSerializer::class)
//    val date: Date? = null, // Дата получения оценки
    val studentName: String,
    val value: Int? = null,
    val courseId: @Contextual Id<CourseNew>,
//    @Serializable(with = DateAsLongSerializer::class)
//    val date: Date? = null,
)

@Serializable
data class CourseNew(
//    @Contextual val courceId: org.litote.kmongo.Id<Course2> = newId(), // id дисциплины (предмета)
    val name: String, // Название дисциплины (предмета)
    @Contextual val id: Id<CourseNew> = newId()
//    val grades: List<Grade> = emptyList()
)






@Serializable
data class Id(
    val name: String,
    val course: String,
)





@Serializable
data class Result(
    val _id: String,
    val grades: Int
)

@Serializable
data class StudentGrade(
    val value: Int? = null,
    @Serializable(with = DateAsLongSerializer::class)
    val date: Date? = null,
)

@Serializable
data class UnwindStudentCourse(
    val name: String,
    val grades: StudentGrade
)

@Serializable
class UnwindCourse(
    val name: String,
    val grades: Grade
)









//@Serializable
//data class Results(
//    val course: String,
//)
//
//@Serializable
//class UnwindCourse(
//    val name: String,
//    val grades: Grade
//)

//@Serializable
//data class Id(
//    val name: String,
//    val course: String,
//)
//
//@Serializable
//data class Result(
//    val _id: lab_4.Id,
//    val grades: Int
//)
//
//@Serializable
//data class DefId(
//    val name: String,
////    val cources: List<String>
//)
//
//@Serializable
//data class Def(
//    val name: String,
//    val courses: String,
//    val sumGrades: Int
//)
//
//@Serializable
//data class DefResult(
//    val _id: DefId,
//    val courses: List<String>,
//    val sumGrades: Int,
//)