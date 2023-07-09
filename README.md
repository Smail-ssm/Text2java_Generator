# Java Class Generator

The Java Class Generator is a tool that automates the process of generating Java classes based on a provided text file. It extracts class definitions from the text file, adds constructors, getters, and setters, and outputs the generated Java classes as separate files.

## Features

- Parses a text file containing Java class definitions
- Generates Java classes with constructors, getters, and setters
- Supports multiple classes within the same text file
- Outputs the generated Java classes as separate files

## Getting Started

### Prerequisites

- Java Development Kit (JDK) installed

### Installation

1. Clone the repository:

    git clone https://github.com/Smail-ssm/Text2java_Generator.git

2. Compile the Java source files:

    javac -d build src/org/example/Main.java
### Usage
Prepare a text file containing the Java class definitions. Each class definition should follow a specific format, with fields and their access modifiers specified.

Run the Java Class Generator program:

 
java -cp build org.example.Main
Follow the program prompts and provide the path to the text file.

The program will extract the class definitions, add constructors, getters, and setters, and generate Java classes.

The generated Java classes will be saved as separate files in the generated_classes directory.

## Configuration
Before running the program, you may need to modify the following configurations:

Input Text File: Provide the path to the text file containing the Java class definitions. Update the textFilePath variable in the Main.java file.
Output Directory: The generated Java classes will be saved in the generated_classes directory by default. You can change the output directory by updating the outputDirectory variable in the Main.java file.
### Example
Let's consider an example text file classes.txt with the following content:

```

public class Student {
    private int id;
    private String name;
    private double grade;
}

public class Teacher {
    private int id;
    private String name;
    private String subject;
}
```
Running the Java Class Generator program with the classes.txt file will generate two Java classes:
Student.java and Teacher.java.
These files will be saved in the generated_classes directory.
Results :
```
public class Student {

    private int id;
    private String name;
    private double grade;

    public Student(int id, String name, double grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    public Student() {
        // Default constructor
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}

public class Teacher {

    private int id;
    private String name;
    private String subject;

    public Teacher(int id, String name, String subject) {
        this.id = id;
        this.name = name;
        this.subject = subject;
    }

    public Teacher() {
        // Default constructor
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
```

# License
This project is licensed under the MIT License.
