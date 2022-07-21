package io.github.t3m8ch.quizbot.models

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("Quizzes")
data class QuizModel(
    @Id val id: ObjectId = ObjectId.get(),
    val createdAt: Instant = Instant.now(),
    var updatedAt: Instant? = null,
    var name: String,
    val questions: MutableList<Question>,
) {
    init {
        if (name.length !in 1..30) {
            // TODO: Change to custom exception
            throw RuntimeException("Quiz name must be between 1 and 30 characters long")
        }
    }
}

data class Question(
    var text: String,
    val possibleAnswers: MutableList<String>,
    val correctAnswerIndex: Int,
) {
    init {
        if (correctAnswerIndex !in 0 until possibleAnswers.size) {
            // TODO: Change to custom exception
            throw RuntimeException("Invalid correctAnswerIndex value")
        }

        if (text.length !in 1..300) {
            // TODO: Change to custom exception
            throw RuntimeException("Question text must be between 1 and 300 characters long")
        }

        if (!possibleAnswers.all { it.length in 1..100 }) {
            // TODO: Change to custom exception
            throw RuntimeException("The possible answer must be between 1 and 100 characters long")
        }
    }
}
