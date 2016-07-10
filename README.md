#Decanting

This Android app allows a user to play a simple puzzle game. The objective of the game is to obtain a specific quantity of liquid in a jug, with the restriction that you can only fill, empty and pour water from the jugs provided.

##Featres
* Nice game mechanics, uses gestures for interactivity and animations
* 10 built-in levels, with custom level generator and random level generator
* About screen, ability to undo moves, retry game, and level verification if it's solvable or reasonable to solve
* Adjustable level generator difficulty
* Solution solver, using a top-down dynamic programming algorithm
* Includes highscores stored in an SQLite database

##Software Design/Architecture
This app makes use of a significant amount of OOP principles. The most essential part of this app is the abstraction of a `Jug` object, which the player interacts with. The `Jug` object should be allowed to be filled, emptied, and then pour into another `Jug`. Another important class would be the `Scenario` class, which would indicate the level and level specific actions such as the goal, current state of jugs, and more. The solver, which includes a test solver, uses a caching BFS algorithm (using a HashSet for caches) to prevent recomputing the overlapping subcases. For highscores, all the scores are stored in an SQLite database, with a `HighscoreItem` encapsulating all of the information. The `GameRenderer` draws the graphics all using relative geometry, so it scales properly on screens. The use of abstraction and OOP principles makes bringing the pieces of this game simple, and it works well and can easily be upgraded in the future.

Feel free to contribute if you want to add any extra cool levels. The level generator will ensure that all levels generated will require at least the difficulty level's amount of steps, with a 10% chance that it will contain a jug with a capacity of 1L.

##Documentation
You can find the documentation in the `doc/` folder.

##Known Issues
There are no known issues.

##Screenshots
![Some Error Occured](/screenshots/screenshotA.png?raw=true "")
![Some Error Occured](/screenshots/screenshotB.png?raw=true "")
![Some Error Occured](/screenshots/screenshotC.png?raw=true "")