# 📚 ShareBooks App

A feature-rich Android application developed as part of a university group project. ShareBooks enables university students to share, search, and interact with secondhand books in a dynamic and user-centric platform. The app emphasizes data structuring, search grammar parsing, interactive features, and privacy-conscious design.

---

## 🚀 Features

### ✅ Core Features
- **User Authentication**: Secure login/logout functionality for users.
- **Structured Dataset**: Custom dataset with over 4,000 book records stored in structured formats (XML, JSON).
- **Data Loading & Visualization**: Book data and user interaction streams are dynamically loaded and displayed.
- **Custom Search Grammar**: Users can search using a formally defined grammar and parser designed from scratch.
- **Search Engine**: Tokenizer and parser handle real-time query parsing, validation, and error handling.

### ✨ Custom Enhancements
#### 🛡 Privacy-Focused Search
- Validity-based search result rendering
- Advanced search result filtering and sorting

#### 📊 Enhanced Data Handling
- **Multiple File Format Support**: Reads data from both XML ([BookDataSource.java](/src/app/src/main/java/anu/g35/sharebooks/data/datasource/BookDataSource.java)) and JSON ([UserDataSource.java](/src/app/src/main/java/anu/g35/sharebooks/data/datasource/UserDataSource.java))
- **User Profile Page**: Includes media files and personal data visualization
- **Graphical Reports**: View summarized app data through graphical components

#### 🤝 User Interactivity
- **Micro-interactions**: Like, dislike, borrow, and return books
- **Follow System**: Users can follow items, with grouped view of all followed entries

---

## 🧪 Testing Summary

### Backend Unit Tests
- Codebase: [`/data` test package](/src/app/src/test/java/anu/g35/sharebooks/data)
- Number of test cases: **98**
- Coverage: **95%**
- Screenshot:  
  ![Data Test Coverage](items/media/test/data.png)

### UI Logic Tests
- Codebase: [`/ui` test package](/src/app/src/test/java/anu/g35/sharebooks/ui)
- Number of test cases: **21**
- Coverage: **95%**
- Screenshot:  
  ![UI Test Coverage](items/media/test/ui.png)

---

## 🧠 Custom Search Grammar

### Grammar Definition

```text
<exp>  ::=  <term>
        |   <term> & <exp>
        |   <term> | <exp>
<term> ::= <key>
        |  ( <exp> )
<key>  ::= ISBN | TITLE | AUTHORS | CATEGORY | YEAR