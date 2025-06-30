package com.example.aiqraft10.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
){

    fun signup(username: String, email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        if (password.length < 8) {
            onResult(false, "Password must be at least 8 characters")
            return
        }
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .build()
                    user?.updateProfile(profileUpdates)?.addOnCompleteListener {
                        onResult(true, null)
                    }
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    fun getMemberSince(): String {
        val user = firebaseAuth.currentUser
        return if (user?.metadata?.creationTimestamp != null) {
            val sdf = SimpleDateFormat("yyyy", Locale.getDefault())
            sdf.format(Date(user.metadata!!.creationTimestamp))
        } else {
            "Unknown"
        }
    }

    fun getCurrentUserId(): String = firebaseAuth.currentUser?.uid.orEmpty()
    fun getCurrentUsername(): String = firebaseAuth.currentUser?.displayName.orEmpty()
    fun logout() = firebaseAuth.signOut()
}