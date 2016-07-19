package astraeus.game.model.entity;

import java.util.LinkedList;

import astraeus.game.model.Position;
import astraeus.game.model.entity.mob.Mob;
import astraeus.game.task.Task;

/**
 * Represents an entity in the game world.
 * 
 * @author Seven
 */
public abstract class Entity {

	/**
	 * The index of an entity in the entity list.
	 */
	protected transient int slot;

	/**
	 * The position of this entity
	 */
	private transient Position position;

	/**
	 * The tasks this entity is performing.
	 */
	private transient final LinkedList<Task> tasks = new LinkedList<Task>();

	public Entity(Position location) {
		this.position = location;
	}

	public abstract int size();
	
	public void onRegister() {
	      
	}
	
	public void onDeregister() {
	      
	}

	public boolean isMob() {
		return getClass() == Mob.class;
	}

	public Mob getMob() {
		return (Mob) this;
	}

	/**
	 * Gets an entities position
	 */
	public Position getPosition() {
		return position;		
	}

	/**
	 * Gets the index of this entity in the entity list.
	 * 
	 * @return The slot.
	 */
	public int getSlot() {
		return slot;
	}

	/**
	 * Gets the tasks that this entity is performing.
	 */
	public LinkedList<Task> getTasks() {
		return tasks;
	}

	/**
	 * The x coordinate of this entity.
	 */
	public int getX() {
		return position.getX();
	}

	/**
	 * The y coordinate of this entity.
	 */
	public int getY() {
		return position.getY();
	}

	/**
	 * Sets a players position to a new coordinate
	 * 
	 * @param location
	 */
	public void setLocation(Position location) {
		this.position = location;
	}

	/**
	 * Sets the index of this entity in the entity list.
	 * 
	 * @param slot
	 *            The index of this entity in the entity list.
	 */
	public void setSlot(int slot) {
		this.slot = slot;
	}

}
