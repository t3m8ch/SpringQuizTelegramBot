package io.github.t3m8ch.quizbot.models

import io.github.t3m8ch.quizbot.fsm.States
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

/**
 * Bot user model. Created every time the bot writes a new user that is not in the collection
 */
@Document("Users")
data class UserModel(
    @Id val id: ObjectId = ObjectId.get(),
    @Indexed(unique = true) val telegramId: Long,
    val createdAt: Instant = Instant.now(),
    val session: UserSession = UserSession.empty(),
)

/**
 * Bot user session. Needed to implement the State machine pattern and
 * store the current information about the user's activity (e.g., current question in a quiz)
 */
data class UserSession(
    var state: States,
    var payload: MutableMap<String, Any?>,
) {
    fun clean() {
        payload = mutableMapOf()
    }

    companion object {
        fun empty() = UserSession(States.INIT, mutableMapOf())
    }
}
