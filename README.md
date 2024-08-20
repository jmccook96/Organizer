# Organizer
CAB302 Fun and more.

# Book Club Organizer

## [Project Overview](https://docs.google.com/document/d/1Ls_yoGWLoRUlNkhaGPD8qo73fY2QtFzXepvobcM5MFw/edit)

**Intended Audience:** Book club members and organizers  
**Project Goal:** To streamline the process of organizing and managing book club meetings.  
**Broader Stakeholders:** Libraries, bookstores, and literary societies.

## Key Features

- User authentication (Sign-Up/Sign-In)
- Event creation and management
- Reading progress tracking
- Book recommendations and reviews
- Collaboration and discussion tools
- Data persistence with a database

## Components Breakdown

### 1. Authentication System
- **Components:**
  - Sign-Up Window: Allows new users to create an account.
  - Sign-In Window: Allows existing users to log in.
  - User Model: Stores user information like username, password (hashed), and profile details.
  - Persistency: Store user data in a database (e.g., SQLite, MySQL).

- **Tickets:**
  1. Create User Model and DAO (Data Access Object) for user-related database operations.
  2. Implement Sign-Up Window with form validation and database integration.
  3. Implement Sign-In Window with authentication logic and error handling.
  4. Encrypt passwords before storing them in the database.
  5. Implement password recovery feature (optional).

### 2. Main Dashboard
- **Components:**
  - Dashboard Window: Central hub where users can access different features.
  - Navigation Menu: Links to different sections like Events, Progress Tracker, Recommendations, etc.

- **Tickets:**
  1. Design the Dashboard Window with navigation menus.
  2. Implement the navigation logic to switch between different sections.
  3. Add user profile information to the dashboard.

### 3. Event Management
- **Components:**
  - Event Creation Window: Allows users to create new book club events.
  - Event List Window: Displays upcoming and past events.
  - Event Model: Stores event details like date, time, location, and participants.
  - Event Notifications: Notify users about upcoming events.

- **Tickets:**
  1. Create Event Model and DAO for event-related database operations.
  2. Implement Event Creation Window with form validation.
  3. Design and implement Event List Window to show all events.
  4. Implement event notification logic (email or in-app notifications).
  5. Implement event editing and deletion functionalities.

### 4. Reading Progress Tracker
- **Components:**
  - Progress Tracker Window: Allows users to log and track their reading progress.
  - Book Model: Stores book information like title, author, and current progress.
  - Progress Model: Stores progress details like pages read, last read date, etc.

- **Tickets:**
  1. Create Book and Progress Models and corresponding DAOs.
  2. Implement Progress Tracker Window with options to add/update/delete progress entries.
  3. Link progress entries to specific events (optional).
  4. Implement a feature to visualize progress (e.g., progress bars or charts).

### 5. Book Recommendations and Reviews
- **Components:**
  - Recommendation Window: Allows users to recommend books to others.
  - Review Window: Allows users to write and view reviews for books.
  - Review Model: Stores reviews and ratings for books.

- **Tickets:**
  1. Create Review Model and DAO for storing book reviews.
  2. Implement Recommendation Window with book search and recommendation options.
  3. Design and implement Review Window for writing/viewing reviews.
  4. Implement a feature to like/dislike or comment on reviews.

### 6. Collaboration Features
- **Components:**
  - Discussion Forum: Allows users to discuss books and events.
  - Chat Window: Real-time chat for book club members.
  - File Sharing: Allows sharing files (e.g., PDFs, images) related to book discussions.

- **Tickets:**
  1. Implement a basic discussion forum using a database to store threads and comments.
  2. Integrate a third-party library or API for real-time chat (e.g., WebSocket).
  3. Implement file upload/download functionality with proper validation.

### 7. Persistency and Database Setup
- **Components:**
  - Database Schema: Design the database schema to store users, events, books, progress, reviews, etc.
  - Database Connection: Implement the logic for connecting to the database.

- **Tickets:**
  1. Design and create the database schema.
  2. Implement database connection logic in Java.
  3. Ensure that all models have corresponding DAOs for database operations.
  4. Implement unit tests for database interactions.

### 8. Final Integration and Testing
- **Components:**
  - Integration: Ensure all components work together seamlessly.
  - Testing: Write unit tests, integration tests, and perform user acceptance testing (UAT).

- **Tickets:**
  1. Integrate all components into a cohesive application.
  2. Write unit and integration tests for each component.
  3. Perform UAT with sample users to identify any issues.
  4. Fix bugs and optimize performance based on feedback.

### 9. Deployment and Documentation
- **Components:**
  - Deployment: Package the application for distribution (e.g., JAR file, installer).
  - Documentation: Write user guides and technical documentation.

- **Tickets:**
  1. Package the application for deployment.
  2. Write user documentation for key features.
  3. Write technical documentation covering the architecture, database schema, and code structure.

### Extras if needed
