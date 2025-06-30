package com.example.aiqraft10.repository

import android.util.Log
import com.example.aiqraft10.data.QuizResult
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import javax.inject.Inject

class FirestoreRepository @Inject constructor(){
    private val db = Firebase.firestore
    private val quizResults = db.collection("quizResults")

    fun saveQuizResult(result: QuizResult) {
        quizResults.add(result)
            .addOnSuccessListener {
                Log.d("Firestore", "Quiz result saved successfully")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Failed to save quiz result: ", e)
            }
    }

    fun getResultsForCurrentUser(onResult: (List<QuizResult>) -> Unit) {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        quizResults
            .whereEqualTo("userId", currentUserId)
            .get()
            .addOnSuccessListener { snapshot ->
                val results = snapshot.documents.mapNotNull { it.toObject(QuizResult::class.java) }
                onResult(results)
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Failed to fetch user specific results: ", e)
                onResult(emptyList())
            }
    }
    fun getUsernameForCurrentUser(onResult: (String) -> Unit) {
        val auth = FirebaseAuth.getInstance()
        val userId = auth.currentUser?.uid ?: return

        quizResults
            .whereEqualTo("userId", userId)
            .limit(1)
            .get()
            .addOnSuccessListener { snapshot ->
                val username = snapshot.documents.firstOrNull()?.getString("username")
                    ?: auth.currentUser?.displayName
                if (username != null) onResult(username)
            }
    }
}