package io.github.t3m8ch.quizbot.models

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("QuizResults")
data class QuizResultModel(
    @Id val id: ObjectId = ObjectId.get(),
    val createdAt: Instant = Instant.now(),
    @DBRef val user: UserModel,
    @DBRef val quiz: QuizModel,
    val answersToQuestions: List<AnswerToQuestion>,
)

data class AnswerToQuestion(
    val questionText: String,
    val answerText: String,
    val isCorrect: Boolean,
)
