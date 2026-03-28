# 🥒 Alekhya Pickles – E-commerce Backend Application

A robust and scalable **backend system** for an e-commerce platform specializing in pickles and homemade products. Built using **Java and Spring Boot**, this application provides secure, production-ready REST APIs for managing users, products, authentication, and orders.

---

## 🚀 Features

### 🔐 Authentication & Security

* Role-Based Access Control (**Admin/User**)
* Custom `UserDetailsService` for secure login handling
* Secured REST APIs using **Spring Security**

### 👤 User Management

* User registration and login
* Role assignment (Admin/User)
* Profile management

### 🛍️ Product Management

* Add, update, delete products (Admin only)
* Fetch all products with:

  * ✅ Pagination
  * ✅ Sorting
  * ✅ Dynamic search (by name, category, etc.)
* Image upload support for product listings

### 🛒 Order & Cart Module

* Add items to cart
* Place orders
* Order status tracking (PLACED, SHIPPED, DELIVERED, CANCELLED)

### ⚙️ Backend Architecture

* Clean layered architecture:

  * Controller → Service → Repository
* RESTful API design principles
* Exception handling with global handlers

---

## 🛠️ Tech Stack

* **Backend:** Java, Spring Boot
* **Security:** Spring Security
* **Database:** MySQL / PostgreSQL
* **ORM:** Hibernate (JPA)
* **Build Tool:** Maven
* **API Testing:** Postman
* **Version Control:** Git & GitHub

---

## 📂 Project Structure

```
com.alekhya
│── controller       # REST Controllers
│── service          # Business Logic
│── repository       # Data Access Layer
│── model            # Entity Classes
│── dto              # Data Transfer Objects
│── security         # Security Config
│── exception        # Exception Handling
│── util             # Utility Classes
```

---

## ⚡ Getting Started

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/your-username/alekhya-pickles.git
cd alekhya-pickles
```

### 2️⃣ Configure Database

Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/alekhya_db
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 3️⃣ Run the Application

```bash
mvn spring-boot:run
```

---

## 🔗 API Endpoints (Sample)

| Method | Endpoint       | Description         |
| ------ | -------------- | ------------------- |
| POST   | /auth/register | Register user       |
| POST   | /auth/login    | Login               |
| GET    | /products      | Get all products    |
| POST   | /products      | Add product (Admin) |
| GET    | /orders        | Get user orders     |
| POST   | /orders        | Place order         |

---

## 🧪 Testing

* Tested APIs using **Postman**
* Validated:

  * Status codes
  * JSON responses
  * Authentication flows

---

## 📌 Future Enhancements

* Payment gateway integration (Razorpay/Stripe)
* Microservices architecture
* Redis caching for performance optimization
* Frontend integration (React)

---

## 👨‍💻 Author

**Jayaram Bellamkonda**
Backend Developer | Java & Spring Boot Enthusiast

---

## ⭐ Contributing

Contributions are welcome!
Feel free to fork the repository and submit pull requests.

---

## 📜 License

This project is open-source and available under the **MIT License**.

---
