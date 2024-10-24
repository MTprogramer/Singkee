package com.Singlee.forex.Repo.Signal

import android.content.Context
import com.Singlee.forex.DataModels.SignalData
import com.Singlee.forex.Repo.Response
import com.Singlee.forex.Utils.Constant
import com.Singlee.forex.Utils.SharedPrefs
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class SignalRepoImp @Inject constructor(
    private val context: Context,
    private val firebaseFirestore: FirebaseFirestore,
    private val sharedPrefs: SharedPrefs
) : SignalsRepo {

    override suspend fun getActiveSignals(isActive : Boolean): Flow<Response<List<SignalData>>> {
        return callbackFlow {

            Constant.userID = sharedPrefs.getString(Constant.USER_ID)
            // Add a snapshot listener for real-time updates
            val listenerRegistration = firebaseFirestore.collection(Constant.SignalCon)
                .whereEqualTo("active", isActive)
                .addSnapshotListener { querySnapshot, e ->
                    if (e != null) {
                        this.trySend(Response.Error(e.message ?: "Unknown error")).isSuccess
                        return@addSnapshotListener
                    }

                    val signals = querySnapshot?.documents?.mapNotNull { document ->
                        document.toObject(SignalData::class.java)
                    } ?: emptyList()

                    // Emit the list of active signals
                    this.trySend(Response.Success(signals)).isSuccess
                }

            awaitClose { listenerRegistration.remove() }
        }.catch { exception ->
            emit(Response.Error(exception.message ?: "Unknown error"))
        }
    }

}
