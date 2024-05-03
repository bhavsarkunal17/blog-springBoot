# Blogging Application

This is a blogging application developed using Java Spring Boot framework. It provides features for managing users and blog posts with secure authentication using Spring Security and JWT tokens.

## Features

- **User Management**: Allows users to sign up, log in, and manage their profiles securely.
- **Blog Posts**: Users can create, edit, delete, and view their blog posts.
- **Authentication and Authorization**: Implements secure authentication and authorization using Spring Security and JWT tokens.
- **Data Persistence**: Utilizes PostgreSQL database with JPA repositories for efficient data management.
- **RESTful APIs**: Implements RESTful APIs for seamless interaction with the application.

## Technologies Used

- **Java**: Programming language used for backend development.
- **Spring Boot**: Provides the foundation for creating robust and scalable applications.
- **Spring Security**: Handles authentication and authorization aspects of the application.
- **JWT (JSON Web Tokens)**: Used for securely transmitting information between parties.
- **PostgreSQL**: Open-source relational database management system used for data storage.
- **JPA (Java Persistence API)**: Standard interface for accessing relational databases from Java.

## Installation

To run this application locally, follow these steps:

1. Clone the GitHub repository: `git clone https://github.com/your-username/Blogging-App.git`
2. Navigate to the project directory: `cd Blogging-App`
3. Build the project using Maven: `mvn clean install`
4. Run the application: `mvn spring-boot:run`

Ensure that you have Java, Maven, and PostgreSQL installed on your system before running the application.

## Usage

Once the application is running, you can access it through your web browser or API client. Use the provided APIs for user management and blog post operations. Refer to the API documentation for details on available endpoints and request formats.
