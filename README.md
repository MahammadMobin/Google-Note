<div align="center">

# 📝 Notes with Gemini

**AI-Powered Notes App**

[![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Room](https://img.shields.io/badge/Room-006D77?style=for-the-badge)]()
[![Gemini](https://img.shields.io/badge/Gemini%20AI-8E75B2?style=for-the-badge)]()

*Modern note-taking app with AI chatbot integration for instant conversational responses*

</div>

---

## 🎯 What is Notes with Gemini?

A production-ready notes application demonstrating modern Android development with AI integration. Built with Jetpack Compose and MVVM architecture, featuring Google Gemini API for intelligent chat interactions and Room Database for offline-first note management.

**Key Features:**
- ✨ AI-powered chatbot using Google Gemini API
- 📝 Complete CRUD operations for notes
- 📌 Pin important notes to top
- 🔍 Search functionality with offline support
- 💾 Offline-first architecture with Room Database
- 🎨 Modern UI with Jetpack Compose

---

## ✨ Core Features

| Feature | Details |
|---------|---------|
| **AI Chatbot** | Instant conversational responses powered by Google Gemini API. |
| **Notes Management** | Create, read, update, delete notes with CRUD operations. |
| **Pin Notes** | Pin important notes to stay at top of the list. |
| **Search & Filter** | Find notes quickly with search functionality. |
| **Offline Support** | Room Database caches all data. Works without internet. |
| **Modern UI** | Built with Jetpack Compose for responsive, declarative UI. |

---

## 🏗️ Architecture

**MVVM + Repository Pattern**

```
UI Layer (Jetpack Compose)
    ↓
ViewModel (State Management)
    ↓
Repository (Data Abstraction)
    ↓
Room Database  |  Retrofit (Gemini API)
```

**Benefits:**
- Clean separation of UI, logic, and data
- Testable and maintainable codebase
- Offline-first with Room Database
- Scalable architecture for future features

---

## 💻 Tech Stack

**Frontend:** Kotlin · Jetpack Compose · Material Design 3  
**AI Integration:** Google Gemini API · Retrofit  
**Local Storage:** Room Database · SharedPreferences  
**Architecture:** MVVM · Repository Pattern  
**Async:** Kotlin Coroutines · Flow

---

## 🚀 Quick Start

```bash
git clone https://github.com/MahammadMobin/Google-Note.git
cd Google-Note

# Add Gemini API key to local.properties:
# GEMINI_API_KEY=your_api_key_here

./gradlew build
```

---

## 🔄 How It Works

**Notes Management:** Create note → Save to Room instantly → Display in UI → Update/Delete with CRUD operations

**AI Chatbot:** Type query → Send to Gemini API via Retrofit → Receive response → Display in chat interface

**Search & Pin:** Search notes locally from Room → Pin feature updates database → UI reflects changes instantly

---

## 🌱 Roadmap

- Voice-to-text notes
- Note categorization with tags
- Cloud sync with Firebase
- Dark mode support
- Export notes to PDF
- AI-powered note summaries

---

## 👨‍💻 Developer

**MD MOBIN** | Android Developer

📧 [osmanfarukmobin@gmail.com](mailto:osmanfarukmobin@gmail.com)  
🔗 [LinkedIn](https://www.linkedin.com/in/mahammad-osman-faruk-mobin-9a677a267/) · [GitHub](https://github.com/MahammadMobin)


---

<div align="center">

**⭐ Star if helpful!**  
*Built with Kotlin & Jetpack Compose*

</div>
