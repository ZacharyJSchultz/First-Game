package com.Zach.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Enemy extends GameObject
{	
	Handler handler;
	
	public Enemy(int x, int y, ID id, Handler handler) 
	{
		super(x, y, id);
		
		this.handler = handler;
		velX = 7;
		velY = 7;
	}
	
	public Rectangle getBounds()
	{
		return new Rectangle(x, y, 32, 32);
	}
	
	public void tick()
	{	
		x += velX;
		y += velY;
		
		if(x <= 0 || x >= Game.WIDTH - 30)
			velX *= -1;
		if(y <= 50 || y >= Game.HEIGHT - 32) 
			velY *= -1;
	}
	public void render(Graphics g)
	{
		g.setColor(Color.RED);
		g.fillOval(x, y, 16, 16);
	}
}