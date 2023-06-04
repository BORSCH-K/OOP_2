package lab_4

import dop.Result
import dop.loadPopulationToDatabase
import org.litote.kmongo.*

val mStudents2 = mongoDatabase.getCollection<Student2>().apply { drop() }
val mCourses2 = mongoDatabase.getCollection<Course2>().apply { drop() }

fun task_4(){

    // Пункт 1
    println("Пункт 1")
    val cources = listOf(
        Course2(name = "Math"),
        Course2(name = "Phys"),
        Course2(name = "History")
    )
    val students = listOf(
        Student2(
            name = "Penny", group = "Girls", grades = listOf(
            Grade2(courseId = cources[0].courceId, courceName = cources[0].name, value = 5),
            Grade2(courseId = cources[1].courceId, courceName = cources[1].name, value = 4),
            Grade2(courseId = cources[2].courceId, courceName = cources[2].name, value = 5),
            Grade2(courseId = cources[0].courceId, courceName = cources[0].name, value = 4),
            Grade2(courseId = cources[1].courceId, courceName = cources[1].name, value = 5),
            Grade2(courseId = cources[2].courceId, courceName = cources[2].name, value = 5)
        )),
        Student2(
            name = "Amy", group = "Girls", grades = listOf(
            Grade2(courseId = cources[0].courceId, courceName = cources[0].name, value = 4),
            Grade2(courseId = cources[1].courceId, courceName = cources[1].name, value = 4),
            Grade2(courseId = cources[2].courceId, courceName = cources[2].name, value = 4),
            Grade2(courseId = cources[0].courceId, courceName = cources[0].name, value = 5),
            Grade2(courseId = cources[1].courceId, courceName = cources[1].name, value = 3),
            Grade2(courseId = cources[2].courceId, courceName = cources[2].name, value = 5)
        )),
        Student2(
            name = "Sheldon", group = "Boys", grades = listOf(
            Grade2(courseId = cources[0].courceId, courceName = cources[0].name, value = 5),
            Grade2(courseId = cources[1].courceId, courceName = cources[1].name, value = 5),
            Grade2(courseId = cources[2].courceId, courceName = cources[2].name, value = 5),
            Grade2(courseId = cources[0].courceId, courceName = cources[0].name, value = 5),
            Grade2(courseId = cources[1].courceId, courceName = cources[1].name, value = 3),
            Grade2(courseId = cources[2].courceId, courceName = cources[2].name, value = 5)
        )),
        Student2(
            name = "Leonard", group = "Boys", grades = listOf(
            Grade2(courseId = cources[0].courceId, courceName = cources[0].name, value = 3),
            Grade2(courseId = cources[1].courceId, courceName = cources[1].name, value = 4),
            Grade2(courseId = cources[2].courceId, courceName = cources[2].name, value = 2),
            Grade2(courseId = cources[0].courceId, courceName = cources[0].name, value = 3),
            Grade2(courseId = cources[1].courceId, courceName = cources[1].name, value = 5),
            Grade2(courseId = cources[2].courceId, courceName = cources[2].name, value = 2)
        )),
        Student2(
            name = "Howard", group = "Boys", grades = listOf(
            Grade2(courseId = cources[0].courceId, courceName = cources[0].name, value = 4),
            Grade2(courseId = cources[1].courceId, courceName = cources[1].name, value = 4),
            Grade2(courseId = cources[2].courceId, courceName = cources[2].name, value = 4),
            Grade2(courseId = cources[0].courceId, courceName = cources[0].name, value = 3),
            Grade2(courseId = cources[1].courceId, courceName = cources[1].name, value = 4),
            Grade2(courseId = cources[2].courceId, courceName = cources[2].name, value = 5)
        )),
        Student2(
            name = "Raj", group = "Boys", grades = listOf(
            Grade2(courseId = cources[0].courceId, courceName = cources[0].name, value = 4),
            Grade2(courseId = cources[1].courceId, courceName = cources[1].name, value = 5),
            Grade2(courseId = cources[2].courceId, courceName = cources[2].name, value = 4),
            Grade2(courseId = cources[0].courceId, courceName = cources[0].name, value = 4),
            Grade2(courseId = cources[1].courceId, courceName = cources[1].name, value = 4),
            Grade2(courseId = cources[2].courceId, courceName = cources[2].name, value = 5)
        ))
    )
    mCourses2.insertMany(cources)
    mStudents2.insertMany(students)

    println("Пункт 2")
    println("Студенты, не имеющие оценки ниже 4 (elemMatch).")
    prettyPrintCursor(
        mStudents2.aggregate<Student2>(
            match(

                not(Student2::grades.elemMatch(Grade2::value.lt(4)))
            )
        )
    )

    println("Пункт 3")
    println("Разворачиваем документ с оценками (unwind).")
    prettyPrintCursor(
        mStudents2.aggregate<UnwindStudents2>(
            match(
//            Student2::grades elemMatch (Grade2::value gt 3),
                not(Student2::grades.elemMatch(Grade2::value.lt(4)))
            ),
            unwind("\$grades")
        )
    )

    println("Пункт 4")
    println("Оставляем поля с именем студента, названием курса и оценкой (project).")
    prettyPrintCursor(
        mStudents2.aggregate<UnwindStudentsCourses2>(
            match(
//            Student2::grades elemMatch (Grade2::value gt 3),
                not(Student2::grades.elemMatch(Grade2::value.lt(4)))
            ),
            unwind("\$grades"),
            project(
                UnwindStudentsCourses2::name from UnwindStudents2::name,
                UnwindStudentsCourses2::course from UnwindStudents2::grades / Grade2::courceName,
                UnwindStudentsCourses2::value from UnwindStudents2::grades / Grade2::value
            )
        )
    )

    println("Пункт 5")
    println("Сумма оценок каждого студента по каждому предмету (group, field).")
    prettyPrintCursor(
        mStudents2.aggregate<Result>(
            match(
//            Student2::grades elemMatch (Grade2::value gt 3),
                not(Student2::grades.elemMatch(Grade2::value.lt(4))) // Студенты, которые не имеют 3-ек (Raj, Penny)
            ),
            unwind("\$grades"),
            project(
                UnwindStudentsCourses2::name from UnwindStudents2::name,
                UnwindStudentsCourses2::course from UnwindStudents2::grades / Grade2::courceName,
                UnwindStudentsCourses2::value from UnwindStudents2::grades / Grade2::value
            ),
            group(
                fields(
                    Id::name from UnwindStudentsCourses2::name,
                    Id::course from UnwindStudentsCourses2::course
                ),
                Result::grades max UnwindStudentsCourses2::value,
            )
        )
    )

}