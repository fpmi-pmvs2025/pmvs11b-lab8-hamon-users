# Easy book access library

**An Android application that enables users to search for books via the Google Books API, view detailed information, and save favorites locally.** 

---

## 📚 Table of Contents
1. [About](#about)  
2. [Features](#features)  
3. [Tech Stack](#tech-stack)  
4. [Installation](#installation)  
5. [Usage](#usage)  
6. [External API Endpoint](#external-api-endpoint)  
7. [Database Schema](#database-schema)  
8. [File Structure](#file-structure)  
9. [Contributing](#contributing)  
10. [Contact](#contact)

---

## About
The **Easy Book Access Library** (root project name: `BookLibrary`) was created as part of the FPMI-PMVS2025 Lab 8 assignment to build a user-centric Android app for discovering and managing books. 

This app leverages the Google Books API to perform full-text searches and retrieve comprehensive book metadata, then allows users to save and manage favorites in a local SQLite database. 

---

## Features
– **Full-text book search** via the Google Books API.   
– **Display detailed book information:** title, subtitle, authors, publisher, published date, description, page count, thumbnail, preview, info, and buy links.   
– **Save favorite books** locally using SQLite database with a primary-key on `previewLink`.   
– **View and delete saved books** through a dedicated activity.   
– **Asynchronous networking** with Volley for fast JSON requests.   
– **Efficient image loading** with Glide for smooth scrolling. 

---

## Tech Stack
– **Android SDK & AGP**: Android Gradle Plugin 8.8.1, compiled with SDK 35, Kotlin DSL Gradle   
– **Language & Compatibility**: Java 11 source and target compatibility   
– **UI Frameworks**: AndroidX AppCompat 1.7.0, Material Components 1.12.0, ConstraintLayout 2.2.1   
– **Networking**: Volley 1.2.1 HTTP library   
– **Image Loading**: Glide 4.16.0   
– **Local Storage**: SQLite via `SQLiteOpenHelper` subclass   
– **Testing**: JUnit 4.13.2, Mockito 5.x, Espresso 3.5.1 

---

## Installation
1. **Go to build directory**
2. **Download .apk on your Android device**
3. **Run android app installation**

---

## Usage

1. Enter a search term in the search bar and tap **🔍 Search** to fetch results from Google Books.
2. Tap the **💾 Save** icon on any book to add it to your local favorites.
3. Tap **Saved Books** to view or delete your favorites.
4. Tap any book item to view full details and open preview or buy links.

---

## External API Endpoint

| Method | URL                                                                             | Description                             |
| ------ | ------------------------------------------------------------------------------- | --------------------------------------- |
| GET    | `/volumes?q={query}`<br>`https://www.googleapis.com/books/v1/volumes?q={query}` | Retrieve books matching the search term |

---

## Database Schema

**Table:** `Books`

| Column          | Type    | Constraint  | Description                    |
| --------------- | ------- | ----------- | ------------------------------ |
| `previewLink`   | TEXT    | PRIMARY KEY | Unique identifier & lookup key |
| `title`         | TEXT    |             | Book title                     |
| `subtitle`      | TEXT    |             | Book subtitle                  |
| `authors`       | TEXT    |             | Comma-separated author names   |
| `publisher`     | TEXT    |             | Publisher name                 |
| `publishedDate` | TEXT    |             | Publication date               |
| `description`   | TEXT    |             | Book description               |
| `pageCount`     | INTEGER |             | Number of pages                |
| `thumbnail`     | TEXT    |             | URL of cover image             |
| `infoLink`      | TEXT    |             | Google info link               |
| `buyLink`       | TEXT    |             | Purchase link                  |

Database managed via a subclass of `SQLiteOpenHelper`.

---

## File Structure

```
/
├─ .github/             # CI workflows
├─ gradle/              # Version catalogs
├─ app/                 # Android application module
│  ├─ build.gradle.kts  # Module build config
│  ├─ src/
│     ├─ main/
│     │  ├─ java/com/hamonusers/booklibrary/      # Source code
│     │  └─ res/layout/                           # UI layouts
│     └─ test/                                   # Unit tests
├─ build.gradle.kts     # Root build config
├─ settings.gradle.kts  # Root project settings (name=BookLibrary) 
└─ README.md
```

---

## Contributing

Authors: Ivan Korol, Katsiaryna Dankova

**Kanban Project Management:** Ivan Korol.

**Creating application architecture:** Katsiaryna Dankova.

**Building DB architecture:** Katsiaryna Dankova.

**CI/CD & Testing:** Ivan Korol.

**Application development:** Ivan Korol, Katsiaryna Dankova.

**Documentation:** Katsiaryna Dankova.

---

## Contact

Maintainers: Ivan Korol, Katsiaryna Dankova

Emails: [qmafog6@gmail.com](mailto:qmafog6@gmail.com), [dankovacdankova@gmail.com](mailto:dankovacdankova@gmail.com) 

GitHub: [https://github.com/fpmi-pmvs2025](https://github.com/fpmi-pmvs2025/pmvs11b-lab8-hamon-users)

