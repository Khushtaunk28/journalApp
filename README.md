# 📓 Journal App

A personal journal application built using **Spring Boot** that allows users to log their thoughts and activities. The app includes features such as admin access, weather integration, and automated email services. It's a secure and scalable backend solution ready to be connected with any frontend.

---

## 🚀 Features

- ✍️ User authentication and journal entry creation
- 🌦️ Weather API integration to log mood with real-time weather
- 📩 Automated email service to remind users or share summaries
- 🔐 Admin panel for user and entry management
- 📚 RESTful API structure with clean MVC architecture
- 🛡️ JWT-based secure authentication

---

## 🧰 Tech Stack

- **Backend:** Spring Boot, Java
- **Database:** MongoDB (can be swapped with SQL-based DBs)
- **Security:** Spring Security + JWT
- **Email:** JavaMailSender
- **Weather:** Integrated using external Weather API (OpenWeatherMap or similar)

---

## 📁 Project Structure

```bash
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.journalapp
│   │   │       ├── controller
│   │   │       ├── model
│   │   │       ├── repository
│   │   │       ├── service
│   │   │       └── config
│   │   └── resources
│   │       ├── application.properties
│   │       └── templates/
│   └── test
│       └── com.journalapp
