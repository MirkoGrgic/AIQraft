# AIQraft - Personalized Quiz App with AI-Generated Questions

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.0-blue.svg)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-1.6.0-green.svg)](https://developer.android.com/compose)
[![Firebase](https://img.shields.io/badge/Firebase-latest-orange.svg)](https://firebase.google.com)

##  Overview

**AIQraft** is a modern Android mobile application developed as a final thesis project at the Faculty of Electrical Engineering, Computer Science and Information Technology Osijek. The app revolutionizes traditional quiz-taking by leveraging Artificial Intelligence to dynamically generate personalized quiz questions based on user preferences.

Unlike conventional quiz apps with static question banks, AIQraft uses the Perplexity AI API to create unique, context-aware questions in real-time, providing an engaging and personalized learning experience.

<div align="center">
  <img src="screenshots/home_screen.png" alt="Home Screen" width="200"/>
  <img src="screenshots/quiz_screen.png" alt="Quiz Screen" width="200"/>
  <img src="screenshots/results_screen.png" alt="Results Screen" width="200"/>
</div>

##  Key Features

### Core Functionality
- **AI-Powered Question Generation**: Dynamic quiz creation using Perplexity AI API
- **Customizable Quizzes**: Users can select topic and number of questions (1-15)
- **Real-time Timer**: Track time spent on each quiz
- **Instant Results**: Immediate feedback with score and detailed analysis

### User Management
- **Secure Authentication**: Email/password sign-up and login via Firebase Authentication
- **User Profiles**: Track total quizzes, average score, best results, and membership duration
- **Personalized Experience**: User-specific data and progress tracking

### Progress Tracking
- **Quiz History**: View last 10 solved quizzes with dates and topics
- **Detailed Review**: Per-question analysis showing correct/incorrect answers
- **Visual Analytics**: Multiple chart types for progress visualization:
  - Pie chart: Correct vs incorrect answers ratio
  - Bar chart: Accuracy by topic
  - Line charts: Accuracy over time and quiz duration trends

### Additional Features
- **Help & Tips Section**: User guidance and app information
- **Loading States**: Visual feedback during quiz generation
- **Material Design 3**: Modern, intuitive UI with Jetpack Compose
- **Offline Support**: Basic functionality available without internet

##  Technology Stack

### Core Technologies
- **Kotlin** - Primary programming language
- **Android Studio** - Development environment
- **Jetpack Compose** - Modern UI toolkit
- **MVVM Architecture** - Clean separation of concerns

### Backend & Services
- **Firebase Authentication** - User management
- **Cloud Firestore** - NoSQL database for quiz results
- **Perplexity AI API** - AI-powered question generation
- **Retrofit** - HTTP client for API communication
- **Gson** - JSON serialization/deserialization

### Dependency Injection
- **Dagger-Hilt** - Dependency injection framework

### UI/UX Libraries
- **Compose Charts** - Data visualization (by tehras)
- **Material Design 3 Components** - Modern UI elements
- **Jetpack Navigation Compose** - Screen navigation

##  Architecture

The project follows clean architecture principles with clear separation of concerns:

```
app/
├── data/           # Data models (Answer, Question, QuizResult)
├── di/             # Dependency injection (FirebaseModule)
├── navigation/     # Navigation routes and destinations
├── network/        # API communication (Perplexity API)
├── repository/     # Data layer (AuthRepository, FirestoreRepository)
├── ui/             # Compose screens and themes
├── viewmodel/      # Business logic (QuizViewModel)
└── MainActivity.kt # Entry point
```

##  Getting Started

### Prerequisites
- Android Studio (latest version recommended)
- JDK 17 or higher
- Android SDK
- Firebase account
- Perplexity AI API key

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/yourusername/aiqraft.git
cd aiqraft
```

2. **Firebase Setup**
- Create a new Firebase project
- Enable Email/Password authentication
- Create a Cloud Firestore database
- Download `google-services.json` and place it in `app/` directory

3. **API Configuration**
- Get your Perplexity AI API key from [Perplexity Docs](https://docs.perplexity.ai)
- Add the API key to your local properties or secure storage

4. **Build and Run**
```bash
./gradlew build
./gradlew installDebug
```

##  Database Structure

### Firestore Collections

**quizResults** collection:
```json
{
  "userId": "string",
  "username": "string",
  "topic": "string",
  "questionCount": "number",
  "correctAnswers": "number",
  "incorrectAnswers": "number",
  "timeTaken": "number",
  "timestamp": "timestamp",
  "questions": [
    {
      "question": "string",
      "answers": ["string"],
      "correctAnswer": "string",
      "userAnswer": "string"
    }
  ]
}
```

##  Key Implementations

### AI Quiz Generation
The app communicates with Perplexity AI API using Retrofit. The generation process:
1. User selects topic and question count
2. System prompt defines AI behavior and response format
3. User prompt specifies the exact quiz requirements
4. Response is parsed into structured Question objects
5. Answers are randomized for fairness

### Authentication Flow
- Secure sign-up with email/password
- Username stored as display name
- Session management with Firebase Auth
- Automatic navigation based on auth state

### Data Visualization
Using Compose Charts library to display:
- **PieChart**: Correct vs incorrect answers
- **BarChart**: Accuracy per topic
- **LineChart**: Performance trends over time

##  Screens

1. **Login/Signup** - User authentication
2. **Home** - Main dashboard with all features
3. **Profile** - User statistics and info
4. **Quiz Setup** - Topic and question count selection
5. **Quiz** - Interactive question-answer with timer
6. **Results** - Score and performance summary
7. **Review** - Detailed question-by-question analysis
8. **History** - Past quizzes list
9. **Progress Charts** - Visual analytics dashboard
10. **Help & Tips** - User guidance

##  Future Enhancements

- **Adaptive Learning**: Generate questions based on user's weak areas
- **Credit System**: Implement freemium model with limited free generations
- **Multi-language Support**: Quizzes in different languages
- **Social Features**: Share results, compete with friends
- **More Question Types**: True/False, fill-in-the-blanks, matching
- **Offline Mode**: Full functionality without internet
- **Export Results**: Share progress as PDF or images


##  Author

**Mirko Grgić**
- Faculty of Electrical Engineering, Computer Science and Information Technology Osijek
- Final Thesis: "Application for Personalized Quizzes with Dynamic Question Generation Using AI"
- Year: 2025

##  Acknowledgments

- Mentor for guidance and support
- Faculty for providing resources and infrastructure
- Perplexity AI for API access
- Open-source community for amazing libraries

---
