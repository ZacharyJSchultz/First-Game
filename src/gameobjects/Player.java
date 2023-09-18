/*
 * Player class, which is a child of GameObject. Handles all collisions as well as instantiating the Player objects.
 */

package gameobjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import game.Game;
import game.HUD;
import game.Handler;
import game.ID;

public class Player extends GameObject
{ 
	static Random r = new Random();
	Handler handler;
	
	// Spawn points for Player
	int randX = 60 + r.nextInt(Game.WIDTH - 50);
	int randY = 60 + r.nextInt(Game.HEIGHT - 50);
	
	// Instantiates player x, y, id, handler, and velocities
	public Player(int x, int y, ID id, Handler handler) 
	{
		super(x, y, id);	
		this.handler = handler;
		velX = 0;
		velY = 0;
	}
	
	// Returns bounds of player as a Rectangle
	public Rectangle getBounds()
	{
		return new Rectangle(x, y, 32, 32);
	}

	@Override
	public void tick()
	{
		// Increases X and Y by X and Y velocities
		x += velX;
		y += velY;
		
		// Clamps the X and Y values so the players cannot go off the screen (48 and 71 were found by testing).
		x = Game.clamp(x, 0, Game.WIDTH - 48);
		y = Game.clamp(y, 50, Game.HEIGHT - 71);
		
		enemyCollision();
		playerCollision();
	}
	
	// Deals with players colliding with enemies
	private void enemyCollision()
	{
		// Iterate through all enemies
		for(int i = 0; i < handler.getEnemies().size(); i++)
		{
			GameObject enemy = handler.getEnemies().get(i);
			
			// Iterate through all players (for each enemy)
			for(int j = 0; j < handler.getPlayers().size(); j++)
			{
				GameObject player = handler.getPlayers().get(j);
				
				// If the bounds of the player intersects with the bounds of the enemy
				if(player.getBounds().intersects(enemy.getBounds()))
				{
					// If not in the countdown before the game starts
					if(HUD.time >= 0 && HUD.count <= 0)
						HUD.health--;
				}
			}
		}
	}
	
	// Deals with players colliding with other players
	private void playerCollision()
	{
		// For each player
		for(int i = 0; i < handler.getPlayers().size(); i++)
		{
			GameObject firstPlayer = handler.getPlayers().get(i);
			
			// For each player (again), starting at i + 1 so no repeats
			for(int j = i + 1; j < handler.getPlayers().size(); j++)
			{
				GameObject secondPlayer = handler.getPlayers().get(j);
				
				// If the loop is not iterating through the same person twice
				if(firstPlayer.getId() != secondPlayer.getId())
				{
					if(firstPlayer.getBounds().intersects(secondPlayer.getBounds()))
					{
						randX = 60 + r.nextInt(Game.WIDTH - 50);
						firstPlayer.x = randX;
						randX = 60 + r.nextInt(Game.WIDTH - 50);
						secondPlayer.x = randX;
						randY = 60 + r.nextInt(Game.HEIGHT - 50);
						firstPlayer.y = randY;
						randY = 60 + r.nextInt(Game.WIDTH - 50);
						secondPlayer.y = randY;
					}
				}
			}
		}
	}
	
	@Override
	public void render(Graphics g) 
	{
		g.setColor(Color.ORANGE);
		g.fillRect(x, y, Game.PLAYER_SIZE, Game.PLAYER_SIZE);
	}
}