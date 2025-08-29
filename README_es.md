# 📝 Task Manager Demo

Aplicación de **demo** construida con **Spring Boot** la cual permite la gestión de tareas (crear, listar, actualizar y eliminar).  
El proyecto utiliza **Java 17**, **Maven**, **Spring Data JPA** y una base de datos en memoria **H2** para fines de demostración.

---

## 🚀 Tecnologías utilizadas
- **Java 17**
- **Spring Boot**
- **Maven**
- **Spring Data JPA**
- **Base de datos H2** (en memoria)
- **Spring Web** (REST API)

---

## 📦 Instalación y ejecución

### 1. Clonar repositorio
```bash
git clone https://github.com/jatella22-git/TaskManagerBackendDemo
cd TaskManagerBackendDemo
```

### 2. Install y ejecución
```bash 
mvn clean install
mvn spring-boot:run
```

### 3. Acceso
- **API disponible en http://localhost:8080**
- **Consola H2 disponible en http://localhost:8080/h2-console**
  - **Driver Class:** org.h2.Driver
  - **JDBC URL:** jdbc:h2:mem:taskdb
  - **Usuario:** sa
  - **Contraseña:** (empty)

---

## 📑 Endpoints iniciales

| MÉTODO | ENDPOINT    | DESCRIPCIÓN                                      |
|--------|-------------|--------------------------------------------------|
| GET    | /tasks      | Lista todas las tareas                           |
| GET    | /tasks/{id} | Obtener el detalle de una tarea filtrando por id |
| POST   | /tasks      | Crea una nueva tareas                            |
| PUT    | /tasks/{id} | Actualiza una tarea                              |
| DELETE | /tasks/{id} | Borra una tarea                                  |

---

## 🛠️ Estructura del proyecto

task-manager-backend
```
├── src
│   ├── main
│   │   ├── java/com/example/task_manager_backend
│   │   │   ├── controller   # Controladores REST
│   │   │   ├── entity       # Entidades JPA
│   │   │   ├── repository   # Repositorios JPA
│   │   │   └── TaskManagerBackendApplication.java
│   │   └── resources
│   │       ├── application.properties
│   │       └── data.sql     # datos iniciales
│   └── test/java/...        # Tests unitarios
```

---

## 🔮 Mejoras futuras

- **Soporte Docker**
- **Autenticación con Spring Security**
- **Uso de Base de Datos real como PostgreSQL**


---


## 🌱 Flujo de ramas (Git Flow)

Este repositorio sigue un flujo de ramas inspirado en **Git Flow**:

- `master`/`main`: rama estable, contiene las versiones listas para producción.  
- `develop`: rama de integración, donde se mergean las funcionalidades antes de pasar a `master`/`main`.  
- `ft/xxxx`: ramas con características/desarrollos nuevas que parten de `develop` y se mergean de vuelta a `develop`.

**Flujo de trabajo habitual:**

ft/funcionalidad → develop → master

De esta forma:
1. Creo una rama `ft/...` desde `develop`.  
2. Trabajo en esa rama y abro un Pull Request hacia `develop`.  
3. Cuando todo está probado e integrado, se hace merge de `develop` a `master`/`main`.  


## 📊 Ejemplo visual del flujo
```
ft/x ----┐
              ├─> develop ----┐
ft/y ----┘               ├─> master/main
                              └─> release
```

---

## 📜 License

### Este proyecto es de uso demostrativo y está bajo la licencia MIT.