## Logistics and package distribution

### Overview
The project involves a system that enables a logistics and distribution company to register and dispatch transfers, dynamically regulating the dispatch of the most profitable transfers as well as those pending delivery for an extended period, in order to prevent what is known as starvation. Additionally, the system collects statistics from the cities where the company operates. To properly understand and operate the system, please consult the [documentation](enunciado.pdf). The documentation is written in Spanish.

### Challenges and insights
This was a collaborative project where we utilized (and, in some cases, implemented) various data structures to solve the problem with optimal efficiency, focusing on time complexity. I am particularly proud of the outcome, as the team demonstrated strong collaboration, and I gained valuable experience in problem analysis and algorithmic solution design. Additionally, I had the opportunity to design and implement a heap from scratch, further deepening my understanding of data structures and algorithmic development.

### How to run
- The code and the tests can be found in the 'implementación' folder, within the 'src' folder. The main class, `BestEffort`, is the entry point of the system and contains the core functions of the system. The other classes serve as auxiliary components that support `BestEffort` to ensure proper functionality.
- To execute the project, it is necessary to have the JDK installed on your device.
- The tests were implemented using the JUnit5 testing framework for Java. Once it is installed, you can execute each test file (or add your own tests) and verify the system.
- To run a test file: If you are using an IDE, it may be necessary to open the folder with all the files (and not just the test file).
1. **From an IDE** (like VS code, Eclipse):
  - Open the test file.
  - Right-click the file or test method and select the option to run the tests.
2. **From the terminal**:
  - **With Maven**:
    `mvn test`
3. **With Gradle**:
    `gradle test` 
