package com.Zach.Game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter
{	
	private Handler handler;
	
	public KeyInput(Handler handler)
	{
		this.handler = handler;
	}
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		//System.out.println(key); //prints key by a number system
		
		for(int i = 0; i < handler.object.size(); i++) //loops through all the objects
		{
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Player || tempObject.getId() == ID.Player2 
			|| tempObject.getId() == ID.Player3 || tempObject.getId() == ID.Player4) //if 1 object in the loop = Player
			{
				if(key == KeyEvent.VK_UP) tempObject.setVelY(-5);
				else if(key == KeyEvent.VK_DOWN) tempObject.setVelY(5);
				else if(key == KeyEvent.VK_LEFT) tempObject.setVelX(-5);
				else if(key == KeyEvent.VK_RIGHT) tempObject.setVelX(5);
				
				else if(key == KeyEvent.VK_W) tempObject.setVelY(-5);
				else if(key == KeyEvent.VK_S) tempObject.setVelY(5);
				else if(key == KeyEvent.VK_A) tempObject.setVelX(-5);
				else if(key == KeyEvent.VK_D) tempObject.setVelX(5);
				
				if(e.toString().length() > 1)
				{
					
				}
			}
		}
	}
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		for(int i = 0; i < handler.object.size(); i++) //loops through all the objects
		{
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Player || tempObject.getId() == ID.Player2 
			|| tempObject.getId() == ID.Player3 || tempObject.getId() == ID.Player4) //if 1 object in the loop = Player
			{
				if(key == KeyEvent.VK_UP) tempObject.setVelY(0);
				else if(key == KeyEvent.VK_DOWN) tempObject.setVelY(0);
				else if(key == KeyEvent.VK_LEFT) tempObject.setVelX(0);
				else if(key == KeyEvent.VK_RIGHT) tempObject.setVelX(0);
				
				else if(key == KeyEvent.VK_W) tempObject.setVelY(0);
				else if(key == KeyEvent.VK_S) tempObject.setVelY(0);
				else if(key == KeyEvent.VK_A) tempObject.setVelX(0);
				else if(key == KeyEvent.VK_D) tempObject.setVelX(0);
			}
		}
		
		if(key == KeyEvent.VK_ESCAPE)
			System.exit(1);
	}
}
