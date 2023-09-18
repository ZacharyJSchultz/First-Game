/*
 * Enemy class, which is a child class of GameObject.
 */

package gameobjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import game.Game;
import game.Handler;
import game.ID;

public class Enemy extends GameObject
{	
	Handler handler;
	
	// Calls GameObject's constructor and initializes handler and velocities
	public Enemy(int x, int y, ID id, Handler handler) 
	{
		super(x, y, id);
		
		this.handler = handler;
		velX = 7;
		velY = 7;
	}
	
	// Returns the bounds of the enemy as a Rectangle object around the enemy, used for collision detection
	public Rectangle getBounds()
	{
		return new Rectangle(x, y, Game.PLAYER_SIZE, Game.PLAYER_SIZE);
	}
	
	// Tick function, which handles enemy collision with walls and changing x/y positions
	public void tick()
	{	
		// Increases coordinates by the velocity of the enemy every tick.
		x += velX;
		y += velY;
		
		// If enemy collides with width or height of window, multiply velocity by -1 (basically bounces off wall)
		if(x <= 0 || x >= Game.WIDTH - 30)
			velX *= -1;
		if(y <= 50 || y >= Game.HEIGHT - 32) 
			velY *= -1;
	}
	
	// Renders the enemy on the screen as an oval
	public void render(Graphics g)
	{
		g.setColor(Color.RED);
		g.fillOval(x, y, Game.PLAYER_SIZE / 2, Game.PLAYER_SIZE / 2);	// Enemies are half the size of the player
		
		// For testing, displays the hitbox of the enemy
		//g.drawRect(x, y, Game.PLAYER_SIZE / 2, Game.PLAYER_SIZE / 2);
	}
}