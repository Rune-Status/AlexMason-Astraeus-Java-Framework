package astraeus.game.model.entity.object.impl;

import astraeus.game.model.Direction;
import astraeus.game.model.Location;
import astraeus.game.model.entity.object.GameObject;
import astraeus.game.model.entity.object.GameObjectType;

import java.util.Objects;

/**
 * Represents a door object in the game world.
 *
 * @author SeVen
 */
public final class Door extends GameObject {

	/**
	 * The modified id of this door.
	 */
	private transient int currentId;

	/**
	 * The modified location of this door.
	 */
	private transient Location currentLocation;

	/**
	 * The flag that denotes the door is open.
	 */
	private boolean open;

	/**
	 * The next orientation.
	 */
	private transient Direction nextOrientation;

	/**
	 * The flag that denotes this door has already been clicked.
	 */
	private transient boolean activated;

	/**
	 * Creates a new {@link Door}.
	 *
	 * @param id       The id of the door.
	 * @param type     The type of door.
	 * @param location The location of this door.
	 */
	public Door(int id, GameObjectType type, Location location, boolean open, Direction orientation) {
		super(id, type, location, orientation);
		this.currentId = id;
		this.currentLocation = location;
		this.open = open;
		this.nextOrientation = orientation;
	}

	/**
	 * Gets the modified id of this door.
	 *
	 * @return The modified id.
	 */
	public int getCurrentId() {
		return currentId;
	}

	/**
	 * Sets the id of this object.
	 *
	 * @param id The new id of this object.
	 */
	public void setCurrentId(int id) {
		this.currentId = id;
	}

	/**
	 * Gets the modified location of this door.
	 *
	 * @return The modified location.
	 */
	public Location getCurrentLocation() {
		return currentLocation;
	}

	/**
	 * Sets the location of this door.
	 *
	 * @param location The new location.
	 */
	public void setCurrentLocation(Location location) {
		this.currentLocation = location;
	}

	/**
	 * Determines if this door is open.
	 *
	 * @return {@code true} If this door is open. {@code false} The door is closed.
	 */
	public boolean isOpened() {
		return open;
	}

	/**
	 * Sets the flag that denotes this door is opened.
	 *
	 * @param opened The flag to set.
	 */
	public void setOpened(boolean opened) {
		this.open = opened;
	}

	/**
	 * Gets the next orientation of this door.
	 *
	 * @return The next orientation.
	 */
	public Direction getNextOrientation() {
		return nextOrientation;
	}

	/**
	 * Sets the next orientation of this door.
	 *
	 * @param nextOrientation The next orientation to set.
	 */
	public void setNextOrientation(Direction nextOrientation) {
		this.nextOrientation = nextOrientation;
	}

	/**
	 * Determines if this door has already been clicked.
	 *
	 * @return {@code true} If this door has been clicked. {@code false} otherwise.
	 */
	public boolean isActivated() {
		return activated;
	}

	/**
	 * Sets the flag that denotes this door has been clicked.
	 *
	 * @param activated The flag to set.
	 */
	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	/**
	 * Determines if this door is a single door.
	 *
	 * @return {@code true} If this door is a single door. {@code false} otherwise.
	 */
	public boolean isSingle() {
		return DoorUtils.SINGLE_DOORS.stream().anyMatch($it -> $it == getCurrentId());
	}

	/**
	 * Determines if this door is a double door.
	 *
	 * @return {@code true} If this door is a double door. {@code false} otherwise.
	 */
	public boolean isDouble() {
		return DoorUtils.DOUBLE_DOORS.stream().anyMatch($it -> $it == getCurrentId());
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o instanceof Door) {
			Door other = (Door) o;
			return (this.hashCode() == other.hashCode());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(currentId, getType(), currentLocation, open, nextOrientation);
	}

	@Override
	public String toString() {
		return String.format("[DOOR] [id= %d] [type= %s] [loc= %s] [open= %s] [orientation= %s]", currentId, getEnumeratedObjectType().name(), currentLocation.toString(), Boolean.toString(open), nextOrientation.name());
	}

}
