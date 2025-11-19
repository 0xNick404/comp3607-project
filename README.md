<a name="top"></a>

<img src="https://i.imgur.com/TEsI8DT.jpeg" width="100%">

![GitHub top language](https://img.shields.io/github/languages/top/0xNick404/comp3607-project)
![GitHub last commit](https://img.shields.io/github/last-commit/0xNick404/comp3607-project)
![Build](https://img.shields.io/github/actions/workflow/status/0xNick404/comp3607-project/maven-build.yml?branch=master)

# COMP 3607 Project

Group project for Object-Oriented Programming II at The UWI St. Augustine Campus done by Nicholas Grimes, Shiann Noriega and Mahaveer Ragbir.

## Table of Contents

- [About](#about)
- [Requirements](#requirements)
- [Usage](#usage)

## About

Implementation of a Multi-Player Jeopardy Game application in Java. The game runs locally on one device and supports 1 to 4 players competing in turn-based gameplay. Game content is loaded from a provided file in CSV, JSON, or XML format. Each player selects a category and question amount, answers, and earns or loses points. At the end of the game, a summary report and a Process Mining event log are generated.

## Requirements

- JDK 21 or newer \
Check with:
```bash
java -version
```

- Apache maven 3.9.11 \
Check with:
```bash
mvn -v
```

- Git \
Check with:
```bash
git --version
```

## Usage

### Windows / Linux / macOS

1. Clone the Repository
```bash
git clone https://github.com/0xNick404/comp3607-project.git
cd comp3607-project
```

2. Build the Application
```bash
mvn clean package
```

3. Run the Game
```bash
java -jar target/comp3607-jeopardy-1.0-SNAPSHOT.jar
```

4. Run Unit Tests
- Run All Tests
```bash
mvn test
```

- Run Specific Test Classes
```bash
mvn test -Dtest=class_name
```

- Run Specific Test Class Methods
```bash
mvn test -Dtest="class_name#method_name"
```


[Back to top](#top)
