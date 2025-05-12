# Billing Software Application

## Overview
This is a billing software application with a front-end built using HTML, CSS, and JavaScript, and a back-end built using Java Servlets, JDBC, and MySQL.

## Project Structure
- `frontend/` - Contains HTML, CSS, and JavaScript files for the user interface.
- `backend/src/com/billing/` - Contains Java source files for database connection and servlets.
- `sql/create_billing_db.sql` - SQL script to create the MySQL database schema and insert sample data.

## Prerequisites
- Java Development Kit (JDK) 8 or higher
- Apache Tomcat or any Java Servlet container
- MySQL Server
- MySQL Connector/J JDBC driver
- Servlet API and JSON library jars (if not using Maven/Gradle)

## Setup Instructions

### 1. Database Setup
- Start your MySQL server.
- Run the SQL script `sql/create_billing_db.sql` to create the database and tables with sample data:
  ```bash
  mysql -u your_mysql_user -p < sql/create_billing_db.sql
  ```
- Update the database connection credentials in `backend/src/com/billing/db/DBConnection.java`:
  ```java
  private static final String USER = "your_mysql_user";
  private static final String PASSWORD = "your_mysql_password";
  ```

### 2. Backend Setup
- Ensure the following libraries are added to your project classpath:
  - Java Servlet API (e.g., `servlet-api.jar`)
  - JSON library (e.g., `json.jar` from https://mvnrepository.com/artifact/org.json/json)
  - MySQL Connector/J JDBC driver
- Compile the Java source files in `backend/src/com/billing/`.
- Deploy the compiled classes and servlet configuration to your servlet container (e.g., Apache Tomcat).

### 3. Frontend Setup
- Place the `frontend/` directory contents in a web-accessible directory or serve via the servlet container.
- Access the application via the login page (`login.html`).

## Running the Application
- Open a browser and navigate to the login page.
- Use the sample users from the database to log in (e.g., username: `user1`, password: `user1password`).
- Generate bills, view transaction history, and update user settings through the UI.

## Testing Instructions

### Frontend Testing
- Test form validations by entering valid and invalid data on all pages.
- Verify error messages and UI behavior.

### Backend API Testing
- Use tools like Postman or Curl to test:
  - `POST /login` for authentication
  - `POST /bills` to create bills
  - `GET /bills` to retrieve bills
- Verify correct responses and error handling.

### Integration Testing
- Test the full user flow from login to bill management and settings update.

## Notes
- Passwords are hashed using SHA-256 in the database.
- For production, consider using HTTPS and stronger security measures.

## Deployment Suggestions
- Host the backend on a secure server with HTTPS enabled.
- Use a connection pool for database connections.
- Consider containerization (Docker) for easier deployment and scalability.

---

If you need assistance with any step or further enhancements, feel free to ask.
