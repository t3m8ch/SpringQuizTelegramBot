package io.github.t3m8ch.quizbot.services

import io.github.t3m8ch.quizbot.models.QuizModel
import org.bson.types.ObjectId

interface QuizService {
    fun getAll(pageIndex: Int = 0, pageSize: Int = 6): List<QuizModel>
    fun getById(id: ObjectId): QuizModel
}
