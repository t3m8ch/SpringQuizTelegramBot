package io.github.t3m8ch.quizbot.services.impl

import io.github.t3m8ch.quizbot.models.QuizModel
import io.github.t3m8ch.quizbot.repositories.QuizRepository
import io.github.t3m8ch.quizbot.services.QuizService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
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
}
