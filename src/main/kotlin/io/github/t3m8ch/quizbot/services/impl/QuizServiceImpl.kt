package io.github.t3m8ch.quizbot.services.impl

import io.github.t3m8ch.quizbot.models.QuizModel
import io.github.t3m8ch.quizbot.repositories.QuizRepository
import io.github.t3m8ch.quizbot.services.QuizService
import org.bson.types.ObjectId
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class QuizServiceImpl(private val quizRepository: QuizRepository) : QuizService {
    override fun getAll(pageIndex: Int, pageSize: Int): List<QuizModel> {
        return quizRepository.findAll(
            PageRequest.of(
                pageIndex,
                pageSize,
                Sort.by(Sort.Direction.DESC, "createdAt")
            )
        ).toList()
    }

    // TODO: Change to custom exception
    override fun getById(id: ObjectId) =
        quizRepository.findByIdOrNull(id) ?: throw RuntimeException("Quiz with id = $id not found")
}
