# Department Asset Management System

A Java Swing application using JDBC and Collections to manage departmental assets. The app includes features like Adding, Updating condition, Deleting, Searching, and Exporting assets.

## Requirements

- **Java JDK** (8 or higher)
- **MySQL Database Server**
- **MySQL JDBC Driver** (e.g., `mysql-connector-java.jar`)

## Database Setup

1. Open your MySQL client (e.g., MySQL Workbench or Command Line).
2. Run the `schema.sql` script to create the database and table:
   ```sql
   source /path/to/AssetManagementSystem/schema.sql;
   ```
   Or simply copy-paste its contents into your client and execute.

## Database Configuration

Before compiling the application, update your database credentials in `DBConnection.java`:
- Open `DBConnection.java`.
- Change the `USER` and `PASSWORD` constants to match your MySQL setup.
  ```java
  private static final String USER = "root";       // Change this
  private static final String PASSWORD = "root";   // Change this
  ```

## Compilation

1. Open a terminal or command prompt in the `AssetManagementSystem` folder.
2. Compile the Java files (Ensure nothing fails):
   ```bash
   javac *.java
   ```

## Running the Application

To run the application, you need to include the MySQL JDBC driver in your classpath. For example, if you have `mysql-connector-j-8.x.x.jar` in the same directory or another known directory:

```bash
# On Windows
java -cp ".;path\to\mysql-connector-j.jar" LoginUI

# On macOS/Linux
java -cp ".:path/to/mysql-connector-j.jar" LoginUI
```

## Usage

1. A login screen will appear. The default hardcoded credentials are:
   - **Username**: admin
   - **Password**: admin
2. Once logged in, you will be taken to the Main Asset Management UI.
3. You can Add, Search, Delete, Export, and Update Condition from there.
