# Console Ludo Game

## About us and the project
We, **Georgi Chitarliev** and **Silvia Popova**, are software engineering students who created a **console-based Ludo game in Java**. This project demonstrates object-oriented programming principles and implements several **design patterns** to ensure modularity, scalability, and maintainability.

---

## Game Rules
- Players roll dice to move their symbols on the board.
- Reaching a ⏹️ (goal tile) earns a point and allows rolling the dice again.
- A player who lands on a tile occupied by another player sends them back to their base and retains the spot, earning another roll.
- ⬛ (safe tiles) protect players from being kicked back.
- Rolling a **6** grants another turn.
- Players must roll a **6** to move out of their base.
- The first player to move all symbols to the goal wins.

---

## Implemented Design Patterns
Our project currently implements several **design patterns**, starting with the **Adapter Pattern**. More patterns will be added, including **Behavioral**, **Structural**, and **Creational** types.

---

## Adapter Design Pattern

### What is an Adapter?
The **Adapter Pattern** is a **structural design pattern** that allows objects with incompatible interfaces to work together. It acts as a bridge between the two interfaces by converting one interface into another that the client expects.

![images/adapter.png](images/adapter.png)

### Why We Used It
In our project, the **Adapter Pattern** was implemented to ensure flexibility between the **console-based interface** and the **game logic**. This separation simplifies unit testing, improves reusability, and supports the addition of new input/output systems without modifying the core game logic.

### Implementation Overview
- **ConsoleAdapter.java** serves as the adapter, translating method calls between the **IGameConsole** interface and the console's specific implementation.
- **IGameConsole.java** defines the required methods for input and output operations.
- The **ConsoleAdapter** class implements **IGameConsole**, providing methods such as `print()` and `readInput()` to interact with users via the console.

### Code Example
```java
// Interface defining console interactions
public interface IGameConsole {
    void print(String message);
    String readInput();
}

// Adapter implementation
import java.util.Scanner;
public class ConsoleAdapter implements IGameConsole {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public String readInput() {
        return scanner.nextLine();
    }
}
```

## Upcoming Design Patterns
We plan to implement the following patterns:
1. **State Pattern** *(Behavioral)* - To handle different player states such as "waiting," "rolling dice," and "moving pieces."  
2. **Builder Pattern** *(Creational)* - To simplify the creation of complex board configurations and setups.  
3. **Observer Pattern** *(Behavioral)* - To manage notifications for player actions and game events.  
4. **Singleton Pattern** *(Creational)* - To ensure a single instance of the **GameEngine**.  
5. **Composite Pattern** *(Structural)* - To group and manipulate board elements like symbols and tiles uniformly.  

---

## How to Run
1. Clone this repository.  
2. Open the project in **IntelliJ IDEA** or any other IDE.  
3. Mark `src/` as the source root under **File > Project Structure > Modules > Sources**.  
4. Build and run the project.  

---

## Authors
- **Georgi Chitarliev** - [GitHub Profile](https://github.com/4ushkopeka)  
- **Silvia Popova** - [GitHub Profile](https://github.com/popo0015)  

---

## License
This project is licensed under the MIT License.