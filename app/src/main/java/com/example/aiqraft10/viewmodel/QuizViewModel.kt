package com.example.aiqraft10.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aiqraft10.data.Answer
import com.example.aiqraft10.repository.FirestoreRepository
import com.example.aiqraft10.data.Question
import com.example.aiqraft10.network.PerplexityRequest
import com.example.aiqraft10.network.PerplexityResponse
import com.example.aiqraft10.network.RetrofitClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import com.example.aiqraft10.repository.AuthRepository
import com.example.aiqraft10.data.QuestionResult
import com.example.aiqraft10.data.QuizResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.delay


@HiltViewModel
class QuizViewModel @Inject constructor(
    private val firestore: FirestoreRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    var quizQuestions by mutableStateOf<List<Question>>(emptyList())
        private set

    var loading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    var correctAnswers by mutableIntStateOf(0)
    var wrongAnswers by mutableIntStateOf(0)

    var duration by mutableIntStateOf(0)
        private set

    var lastQuestionResults by mutableStateOf<List<QuestionResult>>(emptyList())

    var previousResults by mutableStateOf<List<QuizResult>>(emptyList())

    private var timerJob: Job? = null

    fun startTimer() {
        timerJob?.cancel()
        duration = 0
        timerJob = viewModelScope.launch {
            while (isActive) {
                delay(1000L)
                duration += 1
            }
        }
    }
    fun stopTimer() {
        timerJob?.cancel()
    }
    fun resetQuizState() {
        quizQuestions = emptyList()
        correctAnswers = 0
        wrongAnswers = 0
        duration = 0
        lastQuestionResults = emptyList()
        timerJob?.cancel()
    }

    fun logout() {
        authRepository.logout()
    }

    fun generateQuiz(topic: String, questionCount: Int) {
        viewModelScope.launch {
            loading = true
            errorMessage = null
            try {
                val request = PerplexityRequest(
                    messages = listOf(
                        PerplexityRequest.Message(
                            role = "system",
                            content = "Odgovaraj jasno, precizno i u JSON formatu."
                        ),
                        PerplexityRequest.Message(
                            role = "user",
                            content = """
                                    Generiraj $questionCount kviz pitanja na temu "$topic".
                                    Svako pitanje mora imati točno 4 ponuđena odgovora: 1 točan i 3 netočna.
                                    Vrati isključivo JSON u formatu:
                                    {
                                      "questions": [
                                        {
                                          "question": "...",
                                          "answers": [
                                            {"text": "...", "isTrue": true},
                                            {"text": "...", "isTrue": false},
                                            ...
                                          ]
                                        },
                                        ...
                                      ]
                                    }
                                """.trimIndent()
                        )
                    )
                )

                val response = RetrofitClient.apiService.generateQuestions(request)

                if (response.isSuccessful) {
                    val body = response.body()
                    val choices = body?.choices

                    if (!choices.isNullOrEmpty()) {
                        val content = choices.first().message.content
                        Log.d("QuizDebug", "AI response: $content")

                        val questions = parseResponse(body, questionCount)
                        if (questions.isNotEmpty()) {
                            quizQuestions = questions
                        } else {
                            errorMessage = "Error parsing questions."
                            Log.e("QuizDebug", "Parsing failed or no valid questions.")
                        }
                    } else {
                        errorMessage = "AI did not return any questions."
                    }
                } else {
                    errorMessage = "Error fetching questions: ${response.code()} ${response.message()}"
                    Log.e("QuizDebug", "Retrofit error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                errorMessage = e.message
                Log.e("QuizDebug", "Exception: ${e.localizedMessage}")
            } finally {
                loading = false
            }
        }
    }

    private fun parseResponse(response: PerplexityResponse, questionCount: Int): List<Question> {
        val json = response.choices.firstOrNull()?.message?.content ?: return emptyList()

        return try {
            val jsonObject = JSONObject(json)
            val jsonArray = jsonObject.getJSONArray("questions")

            List(jsonArray.length()) { i -> jsonArray.getJSONObject(i) }
                .mapNotNull { item ->
                    val questionText = item.optString("question")
                    val answersJson = item.optJSONArray("answers") ?: return@mapNotNull null

                    val answers = List(answersJson.length()) { j ->
                        val answer = answersJson.getJSONObject(j)
                        Answer(
                            text = answer.optString("text", ""),
                            isTrue = answer.optBoolean("isTrue", false)
                        )
                    }.shuffled()

                    if (questionText.isNotBlank() && answers.size == 4) {
                        Question(question = questionText, answers = answers)
                    } else {
                        null
                    }
                }
                .take(questionCount)
        } catch (_: Exception) {
            emptyList()
        }
    }

    fun saveResult(topic: String, total: Int, correct: Int, wrong: Int, time: Int, questionResults: List<QuestionResult>) {
        val result = QuizResult(
            userId = authRepository.getCurrentUserId(),
            username = authRepository.getCurrentUsername(),
            topic = topic,
            totalQuestions = total,
            correctAnswers = correct,
            incorrectAnswers = wrong,
            durationSeconds = time,
            questions = questionResults
        )
        firestore.saveQuizResult(result)
    }

    fun getUserQuizResults(onResults: (List<QuizResult>) -> Unit) {
        firestore.getResultsForCurrentUser { results ->
            previousResults = results.sortedByDescending { it.timestamp }
            onResults(previousResults)
        }
    }
    fun getCurrentUsernameFromResults(onResult: (String) -> Unit) {
        firestore.getUsernameForCurrentUser(onResult)
    }

    fun getMemberSince(callback: (String) -> Unit) {
        val memberSince = authRepository.getMemberSince()
        callback(memberSince)
    }

}