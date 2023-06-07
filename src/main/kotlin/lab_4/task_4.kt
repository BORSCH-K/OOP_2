package lab_4

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.litote.kmongo.*
import java.util.*


val mStudentsNew = mongoDatabase.getCollection<StudentNew>().apply { drop() }
val mCoursesNew = mongoDatabase.getCollection<CourseNew>().apply { drop() }
val mStud = mongoDatabase.getCollection<S>().apply { drop() }


fun task_4() {

    // Пункт 1

    /*println("Пункт 1")
    val courses = listOf("Math", "Phys", "History").map { CourseNew(it) }
    var i = 0
    val gr = listOf(2, 3, 5, 4, 2, 5, 4, 5, 5, 4, 5, 2, 4, 3, 5, 3, 2, 2)
    val students = listOf("Penny", "Amy").map { name ->
        StudentNew(name, "Girls",
            grades = courses.map {
                GradeNew(courseName = it.name, studentName = name, value = gr[i++], it.id)
            }
        )
    } + listOf("Sheldon", "Leonard", "Howard", "Raj").map { name ->
        StudentNew(name, "Boys",
            grades = courses.map {
                GradeNew(courseName = it.name, studentName = name, value = gr[i++], it.id)
            }
        )
    }
    println(students.joinToString(separator = "\n\n"))
    mCoursesNew.insertMany(courses)
    mStudentsNew.insertMany(students)


    println("Пункт 2")
    println("Студенты, не имеющие оценки ниже 4:")
    prettyPrintCursor(
        mStudentsNew.aggregate<StudentNew>(
            match(not(StudentNew::grades elemMatch (GradeNew::value lte 3)))
    // elemMatch - Создает фильтр, который соответствует всем документам, содержащим свойство, представляющее
    // собой массив, где по крайней мере один элемент массива соответствует заданному фильтру.
        )
    )
    //
    println("Пункт 3")
    println("Разворачиваем документ с оценками:")
    prettyPrintCursor(
        mStudentsNew.aggregate<UnwindStudents>(
            match(not(StudentNew::grades elemMatch (GradeNew::value lte 3))),
            unwind("\$grades")
        )
    )

    println("Пункт 4")
    println("Оставляем поля с именем студента, названием курса и оценкой")
    prettyPrintCursor(
        mStudentsNew.aggregate<UnwindStudentsCourses>(
            match(not(StudentNew::grades elemMatch (GradeNew::value lte 3))),
            unwind("\$grades"),
            project(
                UnwindStudentsCourses::name from UnwindStudents::name,
                UnwindStudentsCourses::courseAndGrade / CourseAndGrade::nameCourse
                        from UnwindStudents::grades / GradeNew::courseName,
                UnwindStudentsCourses::courseAndGrade / CourseAndGrade::grade
                        from UnwindStudents::grades / GradeNew::value
            )
        )
    )*/

    println("Пункт 5")

    val cour = listOf("Math", "Phys", "History").map { C(it) }
    val stud = listOf(
        S(
            "Amy", "А", grades = listOf(
                G("Math", 4, cour[0].id),
                G("Math", 5, cour[0].id),
                G("Math", 3, cour[0].id),
                G("Phys", 3, cour[1].id),
                G("Phys", 4, cour[1].id),
            )
        ),
        S(
            "Alla", "А", grades = listOf(
                G("Math", 3, cour[0].id),
                G("Math", 2, cour[0].id),
                G("Math", 4, cour[0].id),
                G("History", 5, cour[2].id),
                G("History", 3, cour[2].id),
                G("Phys", 4, cour[2].id)
            )
        )
    )
    mStud.insertMany(stud)

    prettyPrintCursor2(
        mStud.aggregate<R>(
            unwind("\$grades"),
            project(
                Temp::name from S::name,
                Temp::course from S::grades / G::courseName,
                Temp::value from S::grades / G::value
            ),
//            project(
//                Temp::name from 1,
//                Temp::course from 1,
//                Temp::value from 1
//            ),
            group(
                fields(
                    I::name from Temp::name,
                    I::course from Temp::course
                ),
//                UnwindStudents::name,
                Result::grades max Temp::value

//                fields(
//                    Result::_id from UnwindStudentsCourses::courseAndGrade / CourseAndGrade::nameCourse,
//                ),
//                Result::grades max UnwindStudentsCourses::courseAndGrade / CourseAndGrade::grade
            )
        )/*.map {
            println(it)
//            println(it._id.name.toString()+ " " + it._id.course.toString() + " "+ it.grades.toString())
        }*/
    )
}


//    prettyPrintCursor(
//        mCoursesNew.aggregate<Result>(
//            unwind("\$grades"),
//            group(
//                fields(
//                    UnwindCourse::grades / Grade::studentName,
//                    Result::grades sum UnwindCourse::grades / Grade::value
//                )
//            )
//        )
//    )
//    println("Сумма оценок каждого студента по каждому предмету (group, field).")
//    prettyPrintCursor(
//        mStudentsNew.aggregate<Result>(
//
//        )
//    )


//    prettyPrintCursor(
//        mStudentsNew.aggregate<Result>(
//            match(
//                not(StudentNew::grades.elemMatch(GradeNew::value.lt(4))) // Студенты, которые не имеют 3-ек (Raj, Penny)
//            ),
//            unwind("\$grades"),
//            project(
//                UnwindStudentsCourses::name from UnwindStudents::name,
//                UnwindStudentsCourses::course from UnwindStudents::grades / GradeNew::courseName,
//                UnwindStudentsCourses::value from UnwindStudents::grades / GradeNew::value
//            ),
//            group(
//                fields(
//                    Id::name from UnwindStudentsCourses::name,
//                    Id::course from UnwindStudentsCourses::course
//                ),
//                Result::grades max UnwindStudentsCourses::value,
//            )
//        )
//    )
//    prettyPrintCursor(
//        mStudentsNew.aggregate<Result>(
//            // match(not(StudentNew::grades elemMatch ( GradeNew::value lte 5))),
//            unwind("\$grades"),
//            project(
//                //UnwindStudentCourse::name from UnwindCourse::name,
//                UnwindStudentsCourses::course from UnwindCourse::grades / GradeNew::courseName,
//                UnwindStudentsCourses::name from UnwindCourse::name,
//                UnwindStudentsCourses::value from UnwindCourse::grades / Grade::value
//            ),
//            group(
//                fields(
//                    Results::course from UnwindStudentsCourses::course,
//                    //     Results::name from UnwindStudentCourse::name,
//                ),
//                Result::grades max UnwindStudentsCourses::value
//            ),
//        ).map { result ->
//            mStudentsNew.find().toList().map {
//                it.grades.filter { it.courseName == result._id.course && it.value == result.grades }
//            }
//        }
//    )


//}


//@Serializable
//data class StudentNew(
//    val name: String, // Имя студента
//    val group: String, // Группа студента (Boys, Girls)
//    var grades: List<GradeNew> = emptyList(), // Коллекция оценок
//    val studentId: @Contextual Id<StudentNew> = newId(), // id студента
////    val course: List<CourseNew> = emptyList()
//)
//
//@Serializable
//data class GradeNew(
////    val studentId: @Contextual Id<StudentNew>,
////    @Contextual val courseId: org.litote.kmongo.Id<Course2>? = null, // id дисциплины (предмета)
//    val courseName: String, //? = null,
////    val value: Int = 0, // Оценка студента
////    @Serializable(with = DateAsLongSerializer::class)
////    val date: Date? = null, // Дата получения оценки
//    val studentName: String,
//    val value: Int? = null,
////    @Serializable(with = DateAsLongSerializer::class)
////    val date: Date? = null,
//)
//
//@Serializable
//data class CourseNew(
////    @Contextual val courceId: org.litote.kmongo.Id<Course2> = newId(), // id дисциплины (предмета)
//    val name: String, // Название дисциплины (предмета)
//    @Contextual val id: Id<CourseNew> = newId()
////    val grades: List<Grade> = emptyList()
//)
//
//
//@Serializable
//data class id(
//    val name: String,
//    val course: String,
//)

//@Serializable
//data class Result(
//    val _id: lab_4.Id,
//    val grades: Int
//)
