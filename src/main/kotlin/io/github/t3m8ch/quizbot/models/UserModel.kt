package io.github.t3m8ch.quizbot.models

import io.github.t3m8ch.quizbot.fsm.States
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("Users")
data class UserModel(
    @Id val id: ObjectId = ObjectId.get(),
    @Indexed(unique = true) val telegramId: Long,
    val createdAt: Instant = Instant.now(),
    val session: UserSession = UserSession.empty(),
)

data class UserSession(
    var state: States,
    var payload: MutableMap<String, Any?>,
) {
    companion object {
        fun empty() = UserSession(States.INIT, mutableMapOf())
    }
}
