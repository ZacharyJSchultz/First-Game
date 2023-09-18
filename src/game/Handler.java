/*
 * This is the handler class, which handles everything to do with the GameObjects.
 * Ticking, rendering, and keeping track of them all in a LinkedList.
 */

package game;

import java.awt.Graphics;
import java.util.LinkedList;

import gameobjects.Enemy;
import gameobjects.GameObject;
import gameobjects.Player;

public class Handler 
{
	private LinkedList<GameObject> objects = new LinkedList<GameObject>();	// Linked list for all objects (players & enemies)
	
	private LinkedList<GameObject> players = new LinkedList<GameObject>();	// Linked list for all players
	private LinkedList<GameObject> enemies = new LinkedList<GameObject>();	// Linked list for all enemies

	public void tick()
	{
		for(int i = 0; i < objects.size(); i++)
		{
			GameObject tempObject = objects.get(i);
			tempObject.tick();
		}
	}
	public void render(Graphics g)
	{
		for(int i = 0; i < objects.size(); i++)
		{
			GameObject tempObject = objects.get(i);
			tempObject.render(g);
		}
	}
	public GameObject addObject(GameObject object)
	{
		this.objects.add(object);
			
		if(object instanceof Player)
			this.players.add(object);
		else if(object instanceof Enemy)
			this.enemies.add(object);
		
		return object;
	}
	public void removeObject(GameObject object)
	{
		this.objects.remove(object);
		
		if(object instanceof Player)
			this.players.remove(object);
		else if(object instanceof Enemy)
			this.enemies.remove(object);
	}
	public LinkedList<GameObject> getObjects()
	{
		return objects;
	}
	public LinkedList<GameObject> getPlayers()
	{
		return players;
	}
	public LinkedList<GameObject> getEnemies()
	{
		return enemies;
	}
}
