# 📝 Task Manager Demo

**Demo** application built with **Spring Boot** that allows task management (create, list, update, and delete).  
The project uses **Java 17**, **Maven**, **Spring Data JPA**, and an in-memory **H2** database for demonstration purposes.

---

## 🚀 Technologies Used
- **Java 17**
- **Spring Boot**
- **Maven**
- **Spring Data JPA**
- **H2 Database** (in-memory)
- **Spring Web** (REST API)

---

## 📦 Installation and Run

### 1. Clone repository
```bash
git clone https://github.com/jatella22-git/TaskManagerBackendDemo
cd TaskManagerBackendDemo
```

### 2. Build and run
```bash 
mvn clean install
mvn spring-boot:run
```

### 3. Access
- **API available at: http://localhost:8080**
- **H2 Console at: http://localhost:8080**
  - **Driver Class:** org.h2.Driver
  - **JDBC URL:** jdbc:h2:mem:taskdb
  - **User:** sa
  - **Password:** (empty)

---

## 📑 Initial Endpoints

| METHOD | ENDPOINT    | DESCRIPTION                     |
|--------|-------------|---------------------------------|
| GET    | /tasks      | Get all tasks                   |
| GET    | /tasks/{id} | Get task detail filtering by id |
| POST   | /tasks      | Create a new task               |
| PUT    | /tasks/{id} | Update an existing task         |
| DELETE | /tasks/{id} | Delete a task                   |

---

## 🛠️ Project Structure

task-manager-backend
```
├── src
│   ├── main
│   │   ├── java/com/example/task_manager_backend
│   │   │   ├── controller   # REST controllers
│   │   │   ├── entity       # JPA entities
│   │   │   ├── repository   # JPA repositories
│   │   │   └── TaskManagerBackendApplication.java
│   │   └── resources
│   │       ├── application.properties
│   │       └── data.sql     # initial data
│   └── test/java/...        # Unit tests
```

---

## 🔮 Future Improvements

- **Docker support**
- **Authentication with Spring Security**
- **Persistence with a real database like PostgreSQL**


---

## 🌱 Branching Workflow (Git Flow)

This repository follows a branching workflow inspired by **Git Flow**:

- `master`/`main`: stable branch, contains production-ready versions.
- `develop`: integration branch, where features are merged before going into `master`/`main`.
- `ft/xxxx`: feature branches created from `develop` and merged back into `develop`.

**Typical workflow:**

ft/feature → develop → master

In this workflow:
1. I create a `ft/...` branch from `develop`.
2. I work on that branch and open a Pull Request into `develop`.
3. Once everything is tested and integrated, `develop` is merged into `master`/`main`.


## 📊 Visual example of the workflow
```
ft/x ----┐
              ├─> develop ----┐
ft/y ----┘               ├─> master/main
                              └─> release
```

---

## 📜 License

### This project is for demonstration purposes and is licensed under the MIT License.