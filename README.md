# Makeup Booking - Service Booking Application

A web application for booking makeup services, designed to streamline the appointment scheduling process and provide secure payment options.

## 💻 Technologies Used
- **Java (21)**  
- **Spring Boot** – Backend framework for rapid application development  
- **MySQL** – Relational database for storing user, appointment, and payment data  
- **Thymeleaf** – Java template engine for dynamic HTML rendering  
- **HTML/CSS** – Frontend design and layout  
- **Firebase** – Real-time notifications and messaging  
- **Redis** – Used for caching to improve performance  
- **RabbitMQ** – Messaging queue for asynchronous task processing  
- **Docker** – Containerization of services for development and deployment  

## 👥 Role
**Team Leader** (Team of 4 Members)
## 🙋‍♂️ Maintainer's Note
This project started as a collaborative effort.  
Currently, I am the only maintainer and continue to actively work on improving and maintaining it.

## 📋 Key Contributions
- Participated in defining project goals and task delegation within the team  
- Assisted in developing the database schema and building user interfaces with Thymeleaf  
- Helped configure **Spring Security** for user authentication and role-based access control  
- Supported the integration of **VNPay** for secure payment processing  
- Implemented **Redis** for efficient caching of frequently accessed data  
- Integrated **RabbitMQ** to handle asynchronous operations such as sending confirmation messages  
- Dockerized the application services for easier setup and deployment  
- Contributed to researching and implementing booking logic tailored to the makeup service industry  

## 🎯 Project Outcome
The project was completed with the following key features:
- ✅ **Secure user authentication**  
- ✅ **Clear role-based access control**  
- ✅ **VNPay payment integration**  
- ✅ **Real-time notifications** using Firebase  
- ✅ **Efficient performance** through Redis caching  
- ✅ **Asynchronous task handling** using RabbitMQ  
- ✅ **Environment consistency** with Docker containerization  

## ⚙️ Spring Profiles (local/dev/prod)

The project now uses profile-based configuration to separate environments cleanly.

- `application.properties`: shared defaults for all environments
- `application-local.properties`: local machine setup (`localhost` services)
- `application-dev.properties`: Docker Compose development setup (`mysql`, `redis-dev`, `rabbitmq`)
- `application-prod.properties`: production-safe setup (all critical hosts/credentials from environment variables)

### Run with profile

- Local (default):
	- `./mvnw spring-boot:run`
- Explicit local:
	- `./mvnw spring-boot:run -Dspring-boot.run.profiles=local`
- Dev:
	- `./mvnw spring-boot:run -Dspring-boot.run.profiles=dev`
- Prod:
	- `./mvnw spring-boot:run -Dspring-boot.run.profiles=prod`

### Environment variables

At minimum, set these values before running:

- `MYSQL_USERNAME`
- `MYSQL_PASSWORD`
- `MAIL_USERNAME`
- `MAIL_PASSWORD`
- `JWT_SECRET`
- `OPENAI_API_KEY`
