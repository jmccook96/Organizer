# BookClubApp

## Overview
This is a JavaFX-based desktop application for organizing and managing book clubs. The application is built using the Model-View-Controller (MVC) pattern, with the backend logic written in Java and the UI defined using FXML. The project structure is organized to promote modularity and ease of maintenance.

## Directory Layout

The following describes the planned directory layout of the project:

```plaintext
BookClubApp/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── bookclub/
│   │   │           ├── Main.java
│   │   │           ├── controller/
│   │   │           │   ├── MainController.java
│   │   │           │   ├── HomeController.java
│   │   │           │   ├── BookClubsController.java
│   │   │           │   ├── BooksController.java
│   │   │           │   ├── EventsController.java
│   │   │           │   ├── ProfileController.java
│   │   │           │   └── ChatController.java
│   │   │           ├── model/
│   │   │           │   ├── User.java
│   │   │           │   ├── BookClub.java
│   │   │           │   ├── Book.java
│   │   │           │   ├── Event.java
│   │   │           │   └── Review.java
│   │   │           ├── dao/
│   │   │           │   ├── UserDAO.java
│   │   │           │   ├── BookClubDAO.java
│   │   │           │   ├── BookDAO.java
│   │   │           │   ├── EventDAO.java
│   │   │           │   └── ReviewDAO.java
│   │   │           ├── util/
│   │   │           │   └── DatabaseManager.java
│   │   │           └── service/
│   │   │               ├── UserService.java
│   │   │               ├── BookClubService.java
│   │   │               ├── BookService.java
│   │   │               ├── EventService.java
│   │   │               └── ReviewService.java
│   │   └── resources/
│   │       └── com/
│   │           └── bookclub/
│   │               ├── main.fxml
│   │               ├── home.fxml
│   │               ├── book_clubs.fxml
│   │               ├── books.fxml
│   │               ├── events.fxml
│   │               ├── profile.fxml
│   │               └── chat.fxml
└── pom.xml
```
------------------------------------------
## Directory Breakdown

### `src/main/java/com/bookclub/`
- **`Main.java`**: The entry point of the application. It initializes the JavaFX application and loads the primary FXML layout.

### `src/main/java/com/bookclub/controller/`
- **Purpose**: Contains all controller classes that handle user interactions and update the UI.
- **Files**:
  - `MainController.java`: Manages the main layout and handles navigation between different screens.
  - `HomeController.java`, `BookClubsController.java`, etc.: Controllers for individual screens.

### `src/main/java/com/bookclub/model/`
- **Purpose**: Contains data classes representing the core entities of the application.
- **Files**:
  - `User.java`: Represents a user with attributes like username, password, email, etc.
  - `BookClub.java`: Represents a book club with attributes like club name, members, current book, etc.
  - `Book.java`: Represents a book with attributes like title, author, rating, etc.
  - `Event.java`: Represents an event with attributes like date, time, location, etc.
  - `Review.java`: Represents a book review with attributes like rating, comment, reviewer, etc.

### `src/main/java/com/bookclub/dao/`
- **Purpose**: Contains classes responsible for interacting with the SQLite database. They perform CRUD (Create, Read, Update, Delete) operations for each model.
- **Files**:
  - `UserDAO.java`, `BookClubDAO.java`, `BookDAO.java`, etc.: DAO classes for managing database operations for their corresponding models.

### `src/main/java/com/bookclub/util/`
- **Purpose**: Contains utility classes that provide common functionalities used across the application.
- **Files**:
  - `DatabaseManager.java`: Manages the SQLite database connection, initializes the database schema, and provides utility methods for database interactions.

### `src/main/java/com/bookclub/service/`
- **Purpose**: Contains service classes that encapsulate the business logic of the application. They interact with DAOs and perform operations required by the controllers.
- **Files**:
  - `UserService.java`, `BookClubService.java`, `BookService.java`, etc.: Service classes that handle business logic for their respective domains.

### `src/main/resources/com/bookclub/`
- **Purpose**: Contains all FXML files that define the UI layouts for different screens.
- **Files**:
  - `main.fxml`: The main layout containing the quick access menu bar and the placeholder for dynamic content.
  - `home.fxml`, `book_clubs.fxml`, `books.fxml`, etc.: FXML files for each screen layout.

### `pom.xml`
- The Maven Project Object Model file, which manages project dependencies, build configurations, and other settings.
