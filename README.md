# 🧮 Simple Calculator App

A basic **Android Calculator App** built in **Java**, designed to perform fundamental arithmetic operations with a clean and intuitive UI.
This app was developed as part of my Android learning journey to strengthen my understanding of **UI components**, **event handling**, and **expression evaluation** in Java.

---

## 🚀 Features

* ➕ **Basic arithmetic operations** — Addition, Subtraction, Multiplication, Division
* 🔢 **Handles multi-digit numbers and decimals**
* 💯 **Percentage (%) support**
* 🔄 **Sign toggle (+/–)** for positive/negative numbers
* 🧹 **Clear (AC)** and **Delete (⌫)** buttons
* 🧠 **Custom infix to postfix expression evaluation logic** (without external libraries)
* 📱 Simple and responsive UI built using **Material Buttons**

---

## 🧩 Tech Stack

* **Language:** Java
* **IDE:** Android Studio
* **UI Framework:** XML layouts + Material Design components

---

## 📷 Screenshots

| Calculator UI                                    |
| ------------------------------------------------ |
| <img width="397" height="811" alt="Screenshot 2025-10-24 221742" src="https://github.com/user-attachments/assets/5e829aae-d8c1-45e3-9c6e-9ac682d96a14" /> |

---

## ⚙️ How It Works

* Uses a `Stack<String>` to manage numbers and operators.
* Converts infix expressions (like `5 + 6 x 2`) to postfix notation for evaluation.
* Handles decimals and signs with proper validation.
* Supports chained operations (e.g., `45 + 23 - 10 / 2`).

---

## 🧱 Folder Structure

```
app/
├── java/
│   └── com.anshali.calculator/
│       └── MainActivity.java
├── res/
│   ├── layout/
│   │   └── activity_main.xml
│   └── values/
│       ├── colors.xml
│       ├── strings.xml
│       └── themes.xml
```

---

## 🔮 Upcoming Improvements

Planned features for the next versions:

* 🎨 **Improved modern UI design** with rounded buttons and gradients
* 🧾 **Calculation History**
* 🧮 **Parentheses ( ) support**

---


## 📚 Learning Highlights

* Used **Stack** data structure for token evaluation
* Practiced **Material Design UI** and layout structuring
* Improved **debugging skills** and **error handling** in Android

---

## 🧑‍💻 Author

**Anshali Gupta**
📍 B.Tech (CSE) Student | Android Developer
💼 [LinkedIn Profile] (https://www.linkedin.com/in/anshali-gupta-2004ag/)
🌐 [GitHub Profile] (https://github.com/AnshaliGupta)

---

⭐ *If you liked this project, give it a star on GitHub — it keeps me motivated to build and learn more!*

