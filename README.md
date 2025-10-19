# Student Grade Management System

A console-based Java application for managing student records, grades, and academic performance tracking.

## Features

- **Add Students**: Register new students with roll number, name, and subject marks
- **View All Students**: Display complete list of all registered students
- **Search Students**: Find specific students by roll number
- **Grade Calculation**: Automatic calculation of total marks, average, and letter grades
- **Data Persistence**: Save and load student data from file storage
- **Input Validation**: Ensures data integrity with proper validation

## Grade Scale

| Average | Grade |
|---------|-------|
| 90-100  | A+    |
| 80-89   | A     |
| 70-79   | B     |
| 60-69   | C     |
| 50-59   | D     |
| 0-49    | F     |

## Requirements

- Java 8 or higher
- No external dependencies required

## How to Run

1. **Compile the program:**
   ```bash
   javac StudentGradeManager.java
   ```

2. **Run the application:**
   ```bash
   java StudentGradeManager
   ```

## Usage

### Main Menu Options

1. **Add Student**
   - Enter unique roll number
   - Provide student name
   - Add subjects with marks (0-100)

2. **View All Students**
   - Displays all registered students
   - Shows roll number, name, total marks, average, and grade
   - Lists all subjects with individual marks

3. **Search Student**
   - Find student by roll number
   - Displays complete student information

4. **Exit**
   - Saves all data to file and exits

### Sample Output

```
=== Student Grade Management System ===
1. Add Student
2. View All Students
3. Search Student by Roll No
4. Exit
Choose an option: 2

--- All Student Records ---
Roll No: 124 | Name: Keerthi | Total: 470 | Avg: 94 | Grade: A+
Subjects:
  Operating System:90
  DBMS:95
  Software Engineering:96
  Probability and Statistics:97
  Computer Networks:92
```

## Data Storage

- Student data is automatically saved to `students.txt`
- File format: `rollNo|name|subject1:marks1|subject2:marks2|...`
- Data is loaded automatically when the program starts
- All changes are saved when exiting the program

## File Structure

```
Student Grade Management System/
├── StudentGradeManager.java    # Main application file
├── students.txt               # Data storage file (auto-generated)
└── README.md                  # This file
```

## Key Classes

### Student Class
- Manages individual student data
- Handles grade calculations
- Provides file serialization methods

### StudentGradeManager Class
- Main application controller
- Handles user interface and menu system
- Manages file I/O operations

## Error Handling

- **Duplicate Roll Numbers**: Prevents adding students with existing roll numbers
- **Invalid Marks**: Validates marks are between 0-100
- **Empty Names**: Ensures student names are not empty
- **File I/O Errors**: Graceful handling of file read/write failures
- **Invalid Input**: Robust input validation for numeric entries

## Future Enhancements

- Edit existing student records
- Delete student records
- Sort students by various criteria
- Export data to CSV format
- Add more detailed reporting features
- GUI interface

## License

This project is open source and available under the MIT License.