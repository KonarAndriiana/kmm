package com.example.kmm.course


class CourseApiService {
    fun getCourse(courseId: Int): Course {
        val courses = listOf(
            Course(
                id = 1,
                title = "KMM Basics",
                description = "Test your knowledge of KMM",
                instructor = Instructor(101, "Janko Hraško", "Senior Kotlin developer"),
                duration = "30 minutes",
                difficulty = "Beginner"
            ),
            Course(
                id = 2,
                title = "Android Dev",
                description = "Basics of Android app development",
                instructor = Instructor(102, "Elena Nováková", "Lead Android Engineer"),
                duration = "40 minutes",
                difficulty = "Intermediate"
            ),
            Course(
                id = 3,
                title = "iOS Dev",
                description = "Basics of iOS app development",
                instructor = Instructor(103, "Peter Horváth", "iOS Developer with 7 years of experience"),
                duration = "35 minutes",
                difficulty = "Intermediate"
            )
        )

        return courses.find { it.id == courseId } ?: Course(
            id = courseId,
            title = "Unknown Test",
            description = "No details available.",
            instructor = Instructor(0, "Unknown", ""),
            duration = "-",
            difficulty = "-"
        )
    }
}