package com.example.kmm.course


class CourseApiService {
    fun getCourse(courseId: Int): Course {
        val courses = listOf(
            Course(
                id = 1,
                title = "KMM Basics",
                description = "Learn the basics of KMM.",
                category = "Mobile",
                instructor = Instructor(101, "Janko Hraško"),
                duration = "30 minutes",
                difficulty = "Beginner"
            ),
            Course(
                id = 2,
                title = "Android UI Design",
                description = "Learn how to create advanced UI in Android.",
                category = "Mobile",
                instructor = Instructor(102, "Elena Nováková"),
                duration = "50 minutes",
                difficulty = "Intermediate"
            ),
            Course(
                id = 3,
                title = "HTML Basics",
                description = "Get to know HTML and build web pages.",
                category = "Frontend",
                instructor = Instructor(103, "Peter Horváth"),
                duration = "40 minutes",
                difficulty = "Beginner"
            ),
            Course(
                id = 4,
                title = "SQL for Beginners",
                description = "Learn the basics of SQL and database queries.",
                category = "Database",
                instructor = Instructor(104, "Mária Kováčová"),
                duration = "45 minutes",
                difficulty = "Beginner"
            )
        )

        return courses.find { it.id == courseId } ?: Course(
            id = courseId,
            title = "Unknown Test",
            description = "No details available.",
            category = " No details available",
            instructor = Instructor(0, "Unknown"),
            duration = "-",
            difficulty = "-",
        )
    }
}