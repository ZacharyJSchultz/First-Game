/*
 * The HUD class controls everything to do with the Heads-Up Display (which is at the top of the screen).
 * That includes the FPS counter, the score, the messages that an enemy is spawning, etc.
 */

package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class HUD 
{
	public static int timer = 0;
	public static int time;				// Acts as score (counts the seconds)
	public static int count = 299;
	public static int health = 200;
	
	// Tick function increments the timer and decrements countdown for start of game
	public void tick()
	{
		if(count <= 0)
		{
			timer++;
			time = timer / 60;		// Converts to seconds
		}
		
		if(count > -1)				// Use -1 so I can check if count < 0 for HUD/collisions
			count--;
		
		// Ensures count never drops below -1 or above 299
		// Starts at 299 so (count / 100) + 1 will be 3 for display (and it's basically one second)
		// Note: Deemed this unnecessary, so commented out since it adds unnecessary runtime to function (though very small)
		//count = Game.clamp(count, -1, 299);
		
		// Ensures health never goes above 1000 or below 0
		health = Game.clamp(health, 0, 1000);
		
		// For debugging
		//System.out.println(time);
	}
	
	public void render(Graphics g)
	{	
		// Creates a box
		g.setColor(Color.BLUE);
		g.fillRect(220, 10, 403, 32);
		
		// Creates a cool outline
		g.setColor(Color.YELLOW);
		g.drawRect(220, 10, 403, 32);
		
		// Health bar
		g.setColor(Color.GRAY);
		g.fillRect(15, 10, 200, 32);
		g.setColor(Color.GREEN);
		g.fillRect(15, 10, health, 32);		// Bar fits 200 health. Multiply/divide if needed (if health =/= 200)
		g.setColor(Color.RED);
		g.drawRect(15, 10, 200, 32);
		
		// FPS counter
		g.setFont(new Font("TimesRoman", Font.BOLD, 15));
		g.setColor(Color.ORANGE);
	    g.drawString("FPS: ", 530, 40);
	    if(Game.getFrames() <= 240)
	    	g.drawString(Game.getFrames() + "", 565, 40);
	    else
	    	g.drawString("240+" + "", 565, 40);
	    
	    // Countdown at the start of the game
	    if(count > 0)
	    {
		    g.setFont(new Font("TimesRoman", Font.BOLD, 15));
			g.setColor(Color.ORANGE);
			
			// Divide by 100 to convert to seconds, and add 1 so the countdown doesn't linger on 0
			// (e.g. 3, 2, 1, START instead of 3, 2, 1, 0, START)
		    g.drawString("Countdown: " + ((count / 100) + 1), 410, 40);
	    }
	    // Prints score if countdown is finished
	    else if(count <= 0)
	    {
		    g.setFont(new Font("TimesRoman", Font.BOLD, 15));
			g.setColor(Color.ORANGE);
		    g.drawString("Score: " + time, 410, 40);
	    }
	    
	    // Prints enemy spawn warning
	    if(time == 9 || time == 19 || time == 29 || time == 39|| time == 10 || time == 20 || time == 30 || time == 40)
	    {
		    g.setFont(new Font("TimesRoman", Font.BOLD, 15));
			g.setColor(Color.RED);
	    	g.drawString("An enemy is spawning!", 230, 38);
	    }
	    // Prints player spawn warning
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
