# 🧪 API Automation Framework - `ApiAutomationDemo`

📁 Project Structure
ApiAutomationDemo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com.api.tests/
│   │   │   │   ├── CreatePetAPI.java
│   │   │   │   ├── CreateUserTestAPI.java
│   │   │   │   └── CreateUserTestAPI2.java
│   │   │   ├── com.api.utils/
│   │   │   │   ├── ConfigManager.java
│   │   │   │   ├── FileHandlingUtils.java
│   │   │   │   └── TestDataGenerator.java
│   ├── resources/
│   │   ├── configs/
│   │   │   ├── dev.properties
│   │   │   └── test.properties
│   │   ├── petOperations/
│   │   │   └── createPetPayload.json
│   │   ├── userOperations/
│   │   │   └── createUserPayload.json
│   │   └── logback.xml
├── testng.xml
├── pom.xml
├── allure-results/
└── README.md

## 🧰 Tech Stack

| Layer              | Technology / Tool         | Purpose                                      |
|-------------------|---------------------------|----------------------------------------------|
| Language           | Java 11                   | Core language for automation                 |
| Build Tool         | Maven                     | Dependency management and build lifecycle    |
| Test Framework     | TestNG                    | Test orchestration and suite management      |
| API Testing        | Rest-Assured              | Fluent API for HTTP requests and assertions  |
| Data Generation    | Java Faker                | Dynamic test data creation                   |
| JSON Handling      | org.json / Gson           | JSON parsing and manipulation                |
| Logging            | SLF4J + Logback           | Structured logging                           |
| Reporting          | Allure                    | Rich test reports with visual insights       |
| Aspect-Oriented    | AspectJ                   | Runtime weaving for Allure integration       |

## 📦 Key Modules

### 🔹 `com.api.tests`
Contains test classes for different API endpoints:
- `CreatePetAPI.java`: Pet creation test logic
- `CreateUserTestAPI.java`: User creation test logic
- `CreateUserTestAPI2.java`: Alternate user test scenarios

### 🔹 `com.api.utils`
Utility classes to support test execution:
- `ConfigManager.java`: Loads environment-specific configs
- `FileHandlingUtils.java`: Reads and writes JSON payloads
- `TestDataGenerator.java`: Uses Java Faker to generate dynamic payloads

---

## ⚙️ Configuration Files

- `dev.properties` / `test.properties`: Environment-specific settings
- `createPetPayload.json` / `createUserPayload.json`: Static payload templates
- `logback.xml`: Logging configuration
- `testng.xml`: Test suite definition

---

## 📈 Reporting

- **Allure** is integrated via Maven and AspectJ
- Results are stored in `/allure-results`
- Run `mvn clean test` followed by `allure serve allure-results` to view reports

---

## 🚀 Getting Started

1. Clone the repo  
2. Install dependencies via `mvn clean install`  
3. Run tests using `mvn test` or via TestNG suite  
4. View reports with Allure  

---

## 👨‍💻 Author

**Chandan Agrawal**  
Automation Enthusiast | API Tester | Java Developer

---
