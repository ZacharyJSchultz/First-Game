package com.Zach.Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class HUD 
{
	public static int timer = 0;
	public static int time;
	public static int count = 500;
	public static int health = 100;
	
	public void tick()
	{
		if(count < 0)
		{
			timer++;
			time = timer / 60;
		}
		count--;
		count = Game.clamp(count, -2, 1000);
		health = Game.clamp(health, 0, 100);
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.YELLOW); //cool outline
		g.drawRect(220, 10, 405, 32);
		
		g.setColor(Color.BLUE); //box
		g.fillRect(220, 10, 405, 32);
		
		g.setColor(Color.GRAY); //health stuff
		g.fillRect(15, 10, 200, 32);
		g.setColor(Color.GREEN);
		g.fillRect(15, 10, health * 2, 32);
		g.setColor(Color.RED);
		g.drawRect(15, 10, 200, 32);
		
		g.setFont(new Font("TimesRoman", Font.BOLD, 15));
		g.setColor(Color.ORANGE); //frames per second
	    g.drawString("FPS: ", 530, 40);
	    if(Game.frames <= 20000)
	    	g.drawString(Game.frames + "", 565, 40);
	    else
	    	g.drawString("20000+" + "", 565, 40);
	    
	    if(count >= 0)
	    {
		    g.setFont(new Font("TimesRoman", Font.BOLD, 15));
			g.setColor(Color.ORANGE); //count down
		    g.drawString("Countdown: " + count / 100, 410, 40);
	    }
	    else if(count < 0)
	    {
		    g.setFont(new Font("TimesRoman", Font.BOLD, 15)); //Dialog is a cool font
			g.setColor(Color.ORANGE); //score
		    g.drawString("Score: " + time, 410, 40);
	    }
	    
	    if(time == 9 || time == 19 || time == 29 || time == 39|| time == 10 || time == 20 || time == 30 || time == 40)
	    {
		    g.setFont(new Font("TimesRoman", Font.BOLD, 15));
			g.setColor(Color.RED);
	    	g.drawString("An enemy is spawning!", 230, 38);
	    }
	    else if(time == 149 || time == 199 || time == 150 || time == 200)
	    {
		    g.setFont(new Font("TimesRoman", Font.BOLD, 15));
			g.setColor(Color.RED);
	    	g.drawString("A player is spawning!!", 230, 38);
	    }
	    else if(time > 200)
	    {
		    g.setFont(new Font("TimesRoman", Font.BOLD, 15));
			g.setColor(Color.ORANGE);
	    	g.drawString("", 230, 38);
	    }
	}
}
