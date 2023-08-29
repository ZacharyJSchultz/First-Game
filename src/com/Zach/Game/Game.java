package com.Zach.Game; 

import java.awt.Canvas; //add option for power ups
import java.awt.Graphics; //Maybe in update make on player collision make it not teleport to random location but just make them stop on collision...
import java.awt.image.BufferStrategy; //TODO Player reaction time is slow fix this!
import java.util.Random; //TODO If 3rd/4th player spawns and goes in another player it spawns another player!

public class Game extends Canvas implements Runnable
{
	private static final long serialVersionUID = -4816714387686029849L; 
	
	public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;
	
	private HUD hud;
	
	Random r = new Random();
	
	public static GameObject player1; //Player variable
	public static GameObject player2;
	public static GameObject player3;
	public static GameObject player4;
	public static GameObject enemy1; //enemy variable
	public static GameObject enemy2;
	public static GameObject enemy3;
	public static GameObject enemy4;
	public static GameObject enemy5;
	
	int randY1 = 60 + r.nextInt(HEIGHT - 50);
	int randX1 = 60 + r.nextInt(WIDTH - 50);
	int randY2 = 60 + r.nextInt(HEIGHT - 50);
	int randX2 = 60 + r.nextInt(WIDTH - 50);
	int randY3 = 60 + r.nextInt(HEIGHT - 50);
	int randX3 = 60 + r.nextInt(WIDTH - 50);
	int randY4 = 60 + r.nextInt(HEIGHT - 50);
	int randX4 = 60 + r.nextInt(WIDTH - 50);
	int enemySpawnsX = 51 + r.nextInt(50);
	int enemySpawnsY = 51 + r.nextInt(50);
	
	static ID id;
	
	private Thread thread;
	private boolean running = false;
	
	static int frames;

	private Handler handler;
	
	public Game()
	{
		handler = new Handler();
		
		this.addKeyListener(new KeyInput(handler));
		new Window(WIDTH, HEIGHT, "Game", this);
		
		hud = new HUD();
		
		player1 = handler.addObject(new Player(randX1, randY1, ID.Player, handler)); //adds player
		player2 = handler.addObject(new Player(randX2, randY2, ID.Player2, handler));
		
		enemy1 = handler.addObject(new Enemy(enemySpawnsX, enemySpawnsY, ID.Enemy, handler)); //adds enemy
	}
	public synchronized void start()
	{
		thread = new Thread(this); //start method
		thread.start();
		running = true;
	}
	public synchronized void stop()
	{
		try
		{
			thread = new Thread(this); //stop method
			thread.join();
			running = false;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public void run()
	{
		this.requestFocus();
		long lastTime = System.nanoTime(); //the run code...
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		frames = 0;
		while(running)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1)
			{
				tick();
				delta--;
			}
			if(running) render();
			
			frames++;
				
			if(System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
				System.out.println("FPS: " + frames);
			}
			
			if(HUD.timer == 600)
			{
				enemy2 = handler.addObject(new Enemy(enemySpawnsX, enemySpawnsY, ID.Enemy2, handler));
			}
			if(HUD.timer == 1200)
			{
				enemy3 = handler.addObject(new Enemy(enemySpawnsX, enemySpawnsY, ID.Enemy3, handler));
			}
			if(HUD.timer == 1800)
			{
				enemy4 = handler.addObject(new Enemy(enemySpawnsX, enemySpawnsY, ID.Enemy4, handler));
			}
			if(HUD.time == 2400)
			{
				enemy5 = handler.addObject(new Enemy(enemySpawnsX, enemySpawnsY, ID.Enemy5, handler));
			}
			if(HUD.timer == 9000)
			{
				player3 = handler.addObject(new Player(randX3, randY3, ID.Player3, handler));
			}
			if(HUD.timer == 12000)
			{
				player4 = handler.addObject(new Player(randX4, randY4, ID.Player4, handler));
			}
			
			if(HUD.health == 0)
			{
				System.exit(1);
				//TODO make death screen and go there... then make an option to go to menu screen or exit game
			}
		}
		stop();
	}
	private void tick()
	{
		handler.tick();
		hud.tick();
	}
	private void render() //renders graphics
	{
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null)
		{
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
	    
		g.fillRect(0, 0,WIDTH, HEIGHT);
		
		handler.render(g);
		hud.render(g);
		
		g.dispose();
		bs.show();
	}
	public static int clamp(int var, int min, int max)
	{
		if(var >= max)
			return var = max;
		else if(var <= min)
			return var = min;
		else
			return var;
	}
	
	public static void main(String[] args)
	{
		new Game();
	}
}