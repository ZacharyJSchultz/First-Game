package com.Zach.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Player extends GameObject
{ 
	static Random r = new Random();
	Handler handler;
	
	int randX = 60 + r.nextInt(Game.WIDTH - 50);
	int randY = 60 + r.nextInt(Game.HEIGHT - 50);

	public Player(int x, int y, ID id, Handler handler) 
	{
		super(x, y, id);	
		this.handler = handler;
		velX = 5;
		velY = 5;
	}
	
	public Rectangle getBounds()
	{
		return new Rectangle(x, y, 32, 32);
	}

	@Override
	public void tick()
	{
		x += velX;
		y += velY;
		
		x = Game.clamp(x, 0, Game.WIDTH - 32);
		y = Game.clamp(y, 50, Game.HEIGHT - 54);
		
		enemyCollision();
		playerCollision();
	}
	
	private void enemyCollision()
	{
		for(int i = 0; i < handler.object.size(); i++)
		{
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Enemy)
			{
				if(getBounds().intersects(tempObject.getBounds()))
				{
					//Collision with other enemy stuff
					if(!(HUD.time >= 0 && HUD.count >= 0))
					{
						HUD.health--;
					}
				}
			}
			if(HUD.time > 10)
			{
				if(tempObject.getId() == ID.Enemy2)
				{
					if(getBounds().intersects(tempObject.getBounds()))
					{
						if(!(HUD.time >= 0) && HUD.count >= 0)
						{
							HUD.health--;
						}	
					}
				}
			}
			if(HUD.time > 20)
			{
				if(tempObject.getId() == ID.Enemy3)
				{
					if(getBounds().intersects(tempObject.getBounds()))
					{
						if(!(HUD.time >= 0) && HUD.count >= 0)
						{
							HUD.health--;
						}	
					}
				}
			}
			if(HUD.time > 30)
			{
				if(tempObject.getId() == ID.Enemy4)
				{
					if(getBounds().intersects(tempObject.getBounds()))
					{
						if(!(HUD.time >= 0) && HUD.count >= 0)
						{
							HUD.health--;
						}	
					}
				}
			}
			if(HUD.time > 40)
			{
				if(tempObject.getId() == ID.Enemy5)
				{
					if(getBounds().intersects(tempObject.getBounds()))
					{
						if(!(HUD.time >= 0) && HUD.count >= 0)
						{
							HUD.health--;
						}	
					}
				}
			}
		}
	}
	private void playerCollision()
	{
		for(int i = 0; i < handler.object.size(); i++)
		{
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Player2)
			{
				if(HUD.timer > 9000)
				{
					if(tempObject.getBounds().intersects(Game.player3.getBounds()))
					{
						tempObject.x = randX;
						randX = 60 + r.nextInt(Game.WIDTH - 50);
						Game.player3.x = randX;
						randX = 60 + r.nextInt(Game.WIDTH - 50);
						Game.player3.y = randY;
						randY = 60 + r.nextInt(Game.HEIGHT - 50);
						tempObject.y = randY;
						randY = 60 + r.nextInt(Game.WIDTH - 50);
						
						if(HUD.count < 0)
							HUD.health--;
					}
				}
				if(HUD.timer > 12000)
				{
					if(tempObject.getBounds().intersects(Game.player4.getBounds()))
					{
						tempObject.x = randX;
						randX = 60 + r.nextInt(Game.WIDTH - 50);
						Game.player4.x = randX;
						randX = 60 + r.nextInt(Game.WIDTH - 50);
						Game.player4.y = randY;
						randY = 60 + r.nextInt(Game.HEIGHT - 50);
						tempObject.y = randY;
						randY = 60 + r.nextInt(Game.WIDTH - 50);
						
						if(HUD.count < 0)
							HUD.health--;
					}
				}
			}
			if(tempObject.getId() == ID.Player)
			{
				if(tempObject.getBounds().intersects(Game.player2.getBounds()))
				{
					tempObject.x = randX;
					randX = 60 + r.nextInt(Game.WIDTH - 50);
					Game.player2.x = randX;
					randX = 60 + r.nextInt(Game.WIDTH - 50);
					Game.player2.y = randY;
					randY = 60 + r.nextInt(Game.HEIGHT - 50);
					tempObject.y = randY;
					randY = 60 + r.nextInt(Game.WIDTH - 50);
					
					if(HUD.count < 0)
						HUD.health--;
				}
				if(HUD.timer > 9000)
				{
					if(tempObject.getBounds().intersects(Game.player3.getBounds()))
					{
						Game.player1.x = randX;
						randX = 60 + r.nextInt(Game.WIDTH - 50);
						Game.player3.x = randX;
						randX = 60 + r.nextInt(Game.WIDTH - 50);
						Game.player3.y = randY;
						randY = 60 + r.nextInt(Game.HEIGHT - 50);
						Game.player1.y = randY;
						randY = 60 + r.nextInt(Game.WIDTH - 50);
						
						if(HUD.count < 0)
							HUD.health--;
					}
				}
				if(HUD.timer > 12000)
				{
					if(tempObject.getBounds().intersects(Game.player4.getBounds()))
					{
						tempObject.x = randX;
						randX = 60 + r.nextInt(Game.WIDTH - 50);
						Game.player4.x = randX;
						randX = 60 + r.nextInt(Game.WIDTH - 50);
						Game.player4.y = randY;
						randY = 60 + r.nextInt(Game.HEIGHT - 50);
						tempObject.y = randY;
						randY = 60 + r.nextInt(Game.WIDTH - 50);
						
						if(HUD.count < 0)
							HUD.health--;
					}
				}
			}
			if(tempObject.getId() == ID.Player2)
			{
				if(HUD.timer > 9000)
				{
					if(tempObject.getBounds().intersects(Game.player3.getBounds()))
					{
						tempObject.x = randX;
						randX = 60 + r.nextInt(Game.WIDTH - 50);
						Game.player3.x = randX;
						randX = 60 + r.nextInt(Game.WIDTH - 50);
						Game.player3.y = randY;
						randY = 60 + r.nextInt(Game.HEIGHT - 50);
						tempObject.y = randY;
						randY = 60 + r.nextInt(Game.WIDTH - 50);
						
						if(HUD.count < 0)
							HUD.health--;
					}
				}
				if(HUD.timer > 12000)
				{
					if(tempObject.getBounds().intersects(Game.player4.getBounds()))
					{
						tempObject.x = randX;
						randX = 60 + r.nextInt(Game.WIDTH - 50);
						Game.player4.x = randX;
						randX = 60 + r.nextInt(Game.WIDTH - 50);
						Game.player4.y = randY;
						randY = 60 + r.nextInt(Game.HEIGHT - 50);
						tempObject.y = randY;
						randY = 60 + r.nextInt(Game.WIDTH - 50);
						
						if(HUD.count < 0)
							HUD.health--;
					}
				}
			}
			if(HUD.timer > 9000)
			{
				if(tempObject.getId() == ID.Player3)
				{
					if(HUD.timer > 12000)
					{
						if(tempObject.getBounds().intersects(Game.player4.getBounds()))
						{
							tempObject.x = randX;
							randX = 60 + r.nextInt(Game.WIDTH - 50);
							Game.player4.x = randX;
							randX = 60 + r.nextInt(Game.WIDTH - 50);
							Game.player4.y = randY;
							randY = 60 + r.nextInt(Game.HEIGHT - 50);
							tempObject.y = randY;
							randY = 60 + r.nextInt(Game.WIDTH - 50);
							
							if(HUD.count < 0)
								HUD.health--;
						}
					}
				}
			}
		}
	}
	
	@Override
	public void render(Graphics g) 
	{
		g.setColor(Color.ORANGE);
		g.fillRect(x, y, 32, 32);
	}
}