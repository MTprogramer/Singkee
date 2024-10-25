package com.Singlee.forex.Repo.Messages

import android.content.Context
import com.Singlee.forex.DataModels.Message
import com.Singlee.forex.Repo.Response
import com.Singlee.forex.Repo.safeApiCall
import com.Singlee.forex.Utils.Constant
import com.Singlee.forex.Utils.SharedPrefs
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class MessageRepoImp @Inject constructor(
    private val context: Context,
    private val firestore: FirebaseFirestore,
    private val sharedPrefs: SharedPrefs
) : MessageRepo {
    override suspend fun sendMessage(message: Message): Flow<Response<Boolean>> {
        return flow {
            val messageWithId =  message
                .copy(
                    profileImage = sharedPrefs.getString(Constant.IMAGE_URL)!!,
                    isSent = true,
                    senderName = sharedPrefs.getString(Constant.NAME)!!,
                    author = sharedPrefs.getBoolean(Constant.AUTHOR)
                )

            val result = safeApiCall(context) {
                firestore.collection("messages").add(messageWithId).await()
                true
            }
            emit(result)
        }.catch {
            emit(Response.Error(it.localizedMessage ?: "Unknown error"))
        }
    }


    //    conversationId: String,
//    limit: Long,
//    lastMessageTimestamp: Long?
    override suspend fun getMessages(): Flow<Response<List<Message>>> = callbackFlow {
        trySend(Response.Loading)
        val query: Query = firestore.collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
//            .limit(limit)

        // If this isn't the first batch, use startAfter for pagination
//        if (lastMessageTimestamp != null) {
//            query = query.startAfter(lastMessageTimestamp)
//        }

        val listener = query.addSnapshotListener { snapshot, error ->
            if (error != null) {
                trySend(Response.Error(error.localizedMessage ?: "Error fetching messages")).isSuccess
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.isEmpty) {
                val messages = snapshot.documents.map { doc ->
                    doc.toObject(Message::class.java)!!
                }
                trySend(Response.Success(messages)).isSuccess
            }
        }

        // Close the flow when the coroutine is canceled
        awaitClose { listener.remove() }
    }
}
