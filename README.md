# Craps Game
A Java Swing GUI implementation of the classic dice game Craps, developed as part of my Computer Programming & Analysis coursework at Fanshawe College.

## About the Game
This application allows multiple players to play Craps through a graphical user interface.

### Features
* Multi-player support for 2–6 players
* Betting system with minimum bets and multiples of $10
* Craps win, lose, and point logic
* Real-time player balance tracking
* Graphical user interface built with Java Swing
* Object-oriented design using multiple Java classes

## Project Structure
```text
src/
├── CrapsGUI.java — Main GUI and game flow
├── Die.java — Die simulation (values 1–6)
├── Game.java — Player list and pot management
├── Pass.java — Bet settlement logic for each round
└── Player.java — Player model (name, balance, bet status)
```

## How to Run
1. Clone the repository:
```bash
git clone https://github.com/david332256/java-oop-project.git
```
2. Navigate to the source directory:
```bash
cd java-oop-project/src
```
3. Compile the Java files:
```bash
javac *.java
```
4. Run the application:
```bash
java CrapsGUI
```

## How to Play
1. Enter the number of players (2–6).
2. Each player starts with $100.
3. Place bets in multiples of $10.
4. Roll the dice.

### First Roll
* **7 or 11** → WIN
* **2, 3, or 12** → LOSE
* Any other number → becomes the **POINT**

### Point Round
* Roll the **POINT** again before a 7 → WIN
* Roll a **7** before the POINT → LOSE

The last player with money wins the pot.

## Technologies Used
* **Java** — Core programming language
* **Java Swing** — GUI framework
* **Object-Oriented Programming** — Classes, methods, encapsulation, and program structure

## Author
**Oluwadarasimi David Adufe**
