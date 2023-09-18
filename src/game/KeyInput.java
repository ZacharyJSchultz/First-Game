/*
 * The KeyInput class reads KeyInput using the KeyAdapter parent class. Could possibly improve fluidity of movement with a different
 * library, or maybe further optimize movement in the future. Because of player presses two movement keys it gets a little weird.
 */

package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import gameobjects.GameObject;

public class KeyInput extends KeyAdapter
{	
	private boolean pressed = false;
	private Handler handler;
	
	public KeyInput(Handler handler)
	{
		this.handler = handler;
	}
	public void keyPressed(KeyEvent e)
	{
		// Idea is to have this ensure that keyPressed has priority over keyReleased for smoother movement. Not sure how effective it is though
		pressed = true;
		
		int key = e.getKeyCode();
		
		//System.out.println(key); // For debugging
		
		// Loops through all Players. If KeyEvent is a movement control, it changes the velocity of the player.
		// This runs fast enough to change all players' velocities  at the same time.
		
		for(int i = 0; i < handler.getPlayers().size(); i++) // Loops through all game objects
		{
			GameObject tempObject = handler.getPlayers().get(i);
			
			if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W)
				tempObject.setVelY(-7);
			else if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S)
				tempObject.setVelY(7);
			else if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)
				tempObject.setVelX(-7);
			else if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)
				tempObject.setVelX(7);
		}
		
		pressed = false;
	}
	public void keyReleased(KeyEvent e)
	{
		if(!pressed)
		{
			int key = e.getKeyCode();
			
			// Same as above function, but sets velocity to 0 if key is pressed.
			for(int i = 0; i < handler.getPlayers().size(); i++)
			{
				GameObject tempObject = handler.getPlayers().get(i);
				
				if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S)
					tempObject.setVelY(0);
				else if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)
					tempObject.setVelX(0);
			}
			
			if(key == KeyEvent.VK_ESCAPE)	// Quits game if escape key released
				System.exit(1);
		}
	}
}
