/*
 * This is the game project, the main class of the Game where everything happens. This class creates all the GameObjects for the players
 * and enemies, initializes the Handler, KeyListener, and HUD, as well as running the game loop and other necessary functions.
 */

package game; 

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

// Possible Ideas: Power ups?? High score?? Difficulties? Death Screen/Main Menu? Improved keyboard input?

import gameobjects.*;

public class Game extends Canvas implements Runnable
{
	private static final long serialVersionUID = -4816714387686029849L; 
	
	// Screen size
	public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9, PLAYER_SIZE = 32;
	
	private HUD hud;
	
	Random r = new Random();
	
	// Player objects (need to be public for Player class collision handling)
	public static GameObject player1;
	public static GameObject player2;
	public static GameObject player3;
	public static GameObject player4;
	
	// Enemy objects
	public static GameObject enemy1;
	public static GameObject enemy2;
	public static GameObject enemy3;
	public static GameObject enemy4;
	public static GameObject enemy5;
	
	// Random x and y variables for the player and enemy spawn locations.
	int randX1 = 60 + r.nextInt(HEIGHT - 50);
	int randY1 = 60 + r.nextInt(WIDTH - 50);
	int randX2 = 60 + r.nextInt(HEIGHT - 50);
	int randY2 = 60 + r.nextInt(WIDTH - 50);
	int randX3 = 60 + r.nextInt(HEIGHT - 50);
	int randY3 = 60 + r.nextInt(WIDTH - 50);
	int randX4 = 60 + r.nextInt(HEIGHT - 50);
	int randY4 = 60 + r.nextInt(WIDTH - 50);
	int enemySpawnsX = 51 + r.nextInt(50);
	int enemySpawnsY = 51 + r.nextInt(50);
	
	static ID id;
	
	private Thread thread;
	private boolean running = false;
	
	private static int frames;	// Total # of frames
	private int prevFrames;		// This variable is used for the updateFPS function, subtracting frames - prevFrames every second to calculate FPS
	public static int FPS;		// Current FPS (used for HUD Class and printing FPS to console)

	private Handler handler;

	// Timer to print FPS every second.
	TimerTask updateFPS = new TimerTask() {
		public void run() {
			FPS = frames - prevFrames;
			prevFrames = frames;
			System.out.println("FPS: " + FPS);
		}
	};
	
	// The constructor, which initializes the Handler, HUD, Window, KeyListener, and creates the first 3 GameObjects (2 players, 1 enemy).
	public Game()
	{
		handler = new Handler();
		
		this.addKeyListener(new KeyInput(handler));
		new Window(WIDTH, HEIGHT, "Game", this);
		
		hud = new HUD();
		
		player1 = handler.addObject(new Player(randX1, randY1, ID.Player, handler)); //adds player
		player2 = handler.addObject(new Player(randX2, randY2, ID.Player2, handler));
		
		enemy1 = handler.addObject(new Enemy(enemySpawnsX, enemySpawnsY, ID.Enemy, handler)); //adds enemy

		Timer t = new Timer();
		t.scheduleAtFixedRate(updateFPS, 1000, 1000);
	}
	
	// Start method, which creates a Thread, starts it, and sets running to true. Calling thread.start() automatically
	// calls run() because of the runnable interface.
	public synchronized void start()
	{
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	// Stops the method by setting running to false and exiting the program
	public synchronized void stop()
	{
		try
		{
			running = false;
			System.exit(1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// The game loop, which calls render(), tick(), keeps track of frames, 
	public void run()
	{
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;				// Tick rate
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		frames = 0;
		while(running)
		{
			// This little block of code calls tick() at the tick rate (60 times per second) by comparing time
			// since last tick with current time. If delta >= 1, then sufficient time has passed to tick()
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1)
			{
				tick();
				delta--;
			}
			
			if(running) render();	// Calls render function all the time
			
			frames++;				// Increments frames

			/* //Prints frames about once every second (It's possible it could be > than 1 second, but not by much)
			if(System.currentTimeMillis() - timer >= 1000)
			{
				timer += 1000;
				System.out.println("Frames: " + frames);
			}*/
			
			// These spawn enemies after a sufficient time has passed, then eventually more players.
			// Used timer instead of HUD.time because need the enemy to spawn only once (and HUD.time will equal
			// the same value 60 times a second, which adds a lot of objects!)
			if(HUD.timer == 10 * amountOfTicks && enemy2 == null)
				enemy2 = handler.addObject(new Enemy(enemySpawnsX, enemySpawnsY, ID.Enemy2, handler));
			else if(HUD.timer == 20 * amountOfTicks && enemy3 == null)
				enemy3 = handler.addObject(new Enemy(enemySpawnsX, enemySpawnsY, ID.Enemy3, handler));
			else if(HUD.timer == 30 * amountOfTicks && enemy4 == null)
				enemy4 = handler.addObject(new Enemy(enemySpawnsX, enemySpawnsY, ID.Enemy4, handler));
			else if(HUD.timer == 40 * amountOfTicks && enemy5 == null)
				enemy5 = handler.addObject(new Enemy(enemySpawnsX, enemySpawnsY, ID.Enemy5, handler));
			else if(HUD.timer == 65 * amountOfTicks && player3 == null)
				player3 = handler.addObject(new Player(randX3, randY3, ID.Player3, handler));
			else if(HUD.timer == 85 * amountOfTicks && player4 == null)
				player4 = handler.addObject(new Player(randX4, randY4, ID.Player4, handler));
			
			// Should be self explanatory, but ends the game if the player's health reaches 0.
			if(HUD.health == 0)
				stop();
		}
	}
	
	// The tick function, which calls two other tick functions
	private void tick()
	{
		handler.tick();		// Calls tick() on every GameObject
		hud.tick();			// Controls HUD
	}
	
	// Renders the graphics of the game, also calling other render functions. 
	// This function uses the BufferStrategy class to draw to the window all at once using
	// a buffer, as opposed to drawing everything one at a time (which is a lot slower).
	// It also allows for "page-flipping", which minimizes screen tear and creates smooth animation.
	private void render()
	{
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null)
		{
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
	    
		g.fillRect(0, 0,WIDTH, HEIGHT);
		
		handler.render(g);		// Calls render for all GameObjects
		hud.render(g);			// Calls render for HUD
		
		g.dispose();			// Dispose graphics, clearing resources and preparing for next getDrawGraphics()
		bs.show();				// Displays graphics to window
	}
	
	// Getter for frames
	public static int getFrames() {
		return frames;
	}
	
	// Basic function used in some other classes that returns the middle number.
	// Ex: clamp(3, 4, 5) returns 4, which is in the middle of 3 and 5 on a number line
	public static int clamp(int var, int min, int max)
	{
		if(var >= max)
			return max;
		else if(var < min)
			return min;
		else
			return var;
	}
	
	public static void main(String[] args)
	{
		new Game();		// Starts the program
	}
}