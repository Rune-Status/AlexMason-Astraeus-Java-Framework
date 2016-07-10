package astraeus.game.model.entity;

import java.util.LinkedList;

import astraeus.game.model.Location;
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
	private transient Location location;

	/**
	 * The tasks this entity is performing.
	 */
	private transient final LinkedList<Task> tasks = new LinkedList<Task>();

	public Entity(Location location) {
		this.location = location;
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
	public Location getLocation() {
		return location;
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
		return location.getX();
	}

	/**
	 * The y coordinate of this entity.
	 */
	public int getY() {
		return location.getY();
	}

	/**
	 * Sets a players position to a new coordinate
	 * 
	 * @param location
	 */
	public void setLocation(Location location) {
		this.location = location;
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
