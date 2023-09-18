/*
 * The GameObject class is a template for the Player and Enemy classes, containing x and y values, x and y velocities,
 * as well as all the functions the Objects must have (like getters, setters, tick, render. etc).
 */

package gameobjects;

import java.awt.Graphics;
import java.awt.Rectangle;

import game.ID;

public abstract class GameObject
{
		protected int x, y, velX, velY;
		public ID id;
		
		// Basic constructor that sets x, y, and ID
		public GameObject(int x, int y, ID id)
		{
			this.x = x;
			this.y = y;
			this.id = id;
		}
		
		public abstract void tick();
		
		public abstract void render(Graphics g);
		
		public abstract Rectangle getBounds();
		
		public void setX(int x) //getters and setters
		{
			this.x = x;
		}
		public void setY(int y)
		{
			this.y = y;
		}
		public int getX()
		{
			return x;
		}
		public int getY()
		{
			return y;
		}
		public void setID(ID id)
		{
			this.id = id;
		}
		public ID getId()
		{
			return id;
		}
		public void setVelX(int velX)
		{
			this.velX = velX;
		}
		public void setVelY(int velY)
		{
			this.velY = velY;
		}
		public int getVelX()
		{
			return velX;
		}
		public int getVelY()
		{
			return velY;
		}
}