# 2D Java Game

![Gameplay Picture 1](/screenshots/Gameplay.png?raw=true)
![Gameplay Picture 2](/screenshots/Gameplay2.png?raw=true)

June 2022 - September 2023.  
  
Last updated: September 18, 2023

### Note:
I mainly worked on this project over the Summer of 2021, however I revisited it in September 2023 to improve readability, fix some bugs, and overall make the project more presentable.

<br>

## Description

### Basic Overview
This is a 2D Game project where the player controls yellow square objects and must avoid red circular enemies that bounce around the screen. The player controls all player objects with either the W/A/S/D keys or the arrow keys, and their time spent alive is their score. If the player gets hit by an enemy, their health will decrease, and if it reaches zero they lose and the game exits. To make the game more challenging, a new enemy spawns every ten seconds, and if you reach a certain threshold then new players will start spawning. Of note, if a player tries to overlap any of the player objects/squares (to artificially make the game easier), all player objects will be randomly teleported somewhere on the screen.

For this project, I designed and coded my own Handler, HUD, Window, KeyInput, and GameObject classes, as well as a Game class that runs the project.

### The Game Class
The Game class is both the class that contains the main method and the class that "manages" all the other classes, ensuring that everything runs. The main method starts things off by calling the Game() constructor, which instantiates the Handler, KeyListener, Window, HUD, and initial Player and Enemy objects. I'll cover what these do later, but the important thing is the Window class calls Game.start() at the end. Game.start() creates a new thread, calls thread.start(), and sets running to true. The Game class both extends Canvas and implements Runnable, which means when thread.start() is called, Game.run() is also called.

### The Run Method
The run method is the most important method in the project. This method is the game loop, which constantly runs (unless the game ends) and ensures that every other class is doing its job. First, it initializes some variables that will be used later, such as frames, lastTime, delta, etc. It then entires a while loop that runs until the game ends, where uses lastTime to calculate the number of nanoseconds that have passed, divides it by ns, and stores it in delta. If delta is greater than one, it calls tick() and subtracts one from delta. Bsaically, without getting too much into the specific math, this ensures that tick() is called at a rate of 60 times per second (the tickrate, which is stored in amountOfTicks). It then calls render(), increments the frames counter, checks the time to see if an enemy or player needs to be spawned, and then checks the player's health (ending the game if it is less than or equal to zero).

### Tick()
The Tick function call primarily handles events, such as changing player positions and detecting collisions. The Tick function call in the Game class is pretty simple, it just calls Handler.tick() and HUD.tick(). 

Handler.tick() also calls more tick functions, iterating through all GameObjects to call their respective tick() functions. The Player object's tick function, as specified in the Player class, increments the X and Y values by the X and Y velocities, uses the clamp() function to ensure the Player objects never move off of the screen, and then calls the playerCollision() and enemyCollision() functions (which will be explained later).

HUD.tick() handles the countdown (before the game starts, decrementing count each time it is called) and keeps track of the time (which is the player's score).

### Render()
The Render function handles everything to do with rendering to the Window, whether it's objects, text, or even the health bar. In the Game class, the render() function uses a BufferStrategy to write to the Window, instantiating the BufferStrategy's graphics and then calling the Handler and HUD's render functions.

The Handler's render function, like it's tick() function, calls the render() function for all GameObjects (which are the Player and Enemy objects). Player.render() draws the player objects, and Enemy.render() draws the enemy objects (using the fillRect() and fillOval() functions).

### Other Classes

The HUD, Handler, and Game classes have basically been covered above. I'll cover the rest of the relevant classes (and any details I missed) below.

#### KeyInput

The KeyInput class uses the KeyAdapter class to read input from the keyboard. The KeyPressed() function loops through all player objects and increments the player objects' velocities depending on which key is pressed. The KeyReleased() function resets the velocity to 0 if the key is released, and if escape is released it exits the game.

#### Window

The Window class is the class that creates the window for the game to be played on. It does this by creating a JFrame, setting some parameters (like the width and height), then it adds the Game object to the JFrame and calls game.start().

#### ID

This class is just an enum with an ID for each player object, if we need to figure out which player we are on specifically. This is useful for the playerCollision() function which will be explained shortly.

#### GameObject

The GameObject class is just a template for the Player and Enemy classes, specifying the functions and variables all GameObjects need (which are mostly getters and setters, as well as tick(), render(), and getBounds() functions). The getBounds() function, for both players and enemies, just returns a new Rectangle() object starting at the object's current X and Y, with a width and height equal to the object's size (32 for players, 16 for enemies).

#### Player

The Player class, aside from tick() and render(), contains two vitally important functions. The first is enemyCollision(), which detects if the enemy collides with a player object, and the second is playerCollision(), which checks if any of the player objects collide.

The enemyCollision() function iterates through all enemy and player objects in a double for loop, and checks if any of them are overlapping using the getBounds() function for each one. If they are (and the game is not in the countdown phase), it subtracts from HUD.health (the player's health).

The playerCollision() function iterates through every player object twice in a nested for loop, and checks if the players are overlapping. If the players are overlapping (and do not have the same ID, because a player object is always overlapping itself), then it will teleport the two player objects to a random location on the map. This is a design choice I implemented that is explained below, under challenges.

## Challenges

Oh boy, there were a lot of challenges.

### Collision detection. 

I wasn't sure how exactly I wanted to handle collision detection going into the project, and there are multiple types of collisions to cover. There are collisions with the border of the Window, collisions between the enemies and players, and collisions between players and players.

For collisions with the screen border, I used the clamp() function to ensure that the Player's X and Y values were never greater or less than the edges of the screen. For enemy collisions, if they reached the edge of the screen I multiplied their velocities by -1 (which simulated bouncing off the screen).

For players colliding with enemies, first there's some background. Going into the project, I wanted players to be one shape and enemies another. I chose squares for players and circles for enemies, but I wasn't sure how to check if they collided with each other. Coding an entire collision handler would be pretty time-consuming, so what I ended up doing was utilizing the Java built-in libraries, specifically the Rectangle() function. The Rectangle class has an intersect() function that would let me check if two rectangles are intersecting, so I'm halfway there. But how do I handle enemies, who are circles? I didn't want to turn them into squares as well (because having them be different shapes would help those who are colorblind or visually impaired), so I ended up coming up with the solution of creating a new Rectangle object in the getBounds() function. If I create a Rectangle around the enemies that is the same size at the same location, then I can use the Rectangle function intersects() to check if the hitboxes of the player and enemy are overlapping. This means enemies hitboxes are technically a tiny bit larger in the corners than their actual size, but it's so miniscule that I deemed it negligible.

For players and players, this was more of a design problem, as I could just use the same method as above to deal with collision detection. But I wasn't sure what I wanted my game to do while my players were colliding. Should I make the players stop next to each other? But then the players are stuck next to each other for the rest of the game. I could also do nothing, but then the players could enter inside of each other (and get stuck there), which would essentially be playing with one player now. Both of these solutions create a very boring meta strategy, so I decided to make it so the players will teleport to a random location after colliding. This adds a little more randomness to the game and creates a fun strategy where it's like "oh, if I'm gonna definitely get hit, I can collide my players to teleport somewhere and hope I get lucky," though you could enter into either a better or worse situation than before.

### Overlapping Error

Another error that popped up was a problem where multiple enemies would overlap, almost looking like some snakelike creature. Also, tens of players would spawn in random locations at the set times. The problem was that I was using HUD.time instead of HUD.timer, because HUD.time is counted in seconds. The tick rate of the game is 60, which means the tick() function is called 60 times a second, which means HUD.time is the same value for 60 seconds, so 60(ish) enemies would spawn and move in the same direction, and 60 players would spawn at the set time. Changing this to HUD.timer fixed this (mostly, see below).

After fixing this problem, I tested it and the players would still spawn a bunch (until the game crashed), and I was very confused, certain I had just solved the problem. Turns out an if statement in playerCollision() was set to == instead of !=, so instead of checking if the player object was different during player collision, it would check if they had the same ID. And if they were the same, it would check if they were colliding (which they were, since they are in the same position), and so it would teleport both objects (which are the same) to different locations, spawning an extra player. Except it would do this hundreds of times until the game froze.

### Health Error

Another problem that popped up after fixing these problems was that my health would decrease way too much upon colliding with an enemy. Through debugging, I figured out that tens of objects (With the same ID) were being added to the handler lists. After more testing and debugging, I realized that tick() is not called often enough to increment HUD.timer before the run() loop reaches the same if statements to call handler.addObject(), so I added a requirement to the if statements that there is no object currently stored in the respective GameObject variable.

## Running the Project

To run the project, just double click on the .JAR file! But make sure you have Java installed (if you don't, you can install it from here: https://www.java.com/en/download/help/download_options.html).

If that doesn't work, you can also navigate to the area the file is stored in a Terminal and run:
java -jar First-Game.jar

## License

The license is GNU GPL license. For more info see license.txt.

## Enjoy!
