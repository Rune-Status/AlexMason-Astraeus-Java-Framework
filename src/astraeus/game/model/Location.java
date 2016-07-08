package astraeus.game.model;

import astraeus.game.model.entity.Entity;

import java.util.Objects;

public final class Location {

	/**
	 * The maximum distance a mobile entity can see.
	 */
	public static final int VIEWING_DISTANCE = 15;

	/**
	 * The number of height levels it takes to reach a new plane.
	 */
	public static final int HEIGHT_LEVELS = 4;

	/**
	 * The x coordinate on a grid.
	 */
	private int x;

	/**
	 * The y coordinate on a grid.
	 */
	private int y;

	/**
	 * The height of this point.
	 */
	private int height;

	/**
	 * Creates a new {@link Location} with a default {@code height} of {@code 0}
	 * .
	 * 
	 * @param x
	 *            The x coordinate on a grid.
	 * @param y
	 *            The y coordinate on a grid.
	 */
	public Location(int x, int y) {
		this(x, y, 0);
	}

	/**
	 * Creates a new {@link Location}.
	 * 
	 * @param x
	 *            The x coordinate on a grid.
	 * @param y
	 *            The y coordinate on a grid.
	 * @param height
	 *            The height or plane.
	 */
	public Location(int x, int y, int height) {
		this.x = x;
		this.y = y;
		this.height = height;
	}

	/**
	 * Creates a new {@link Location} from an existing {@code location}.
	 * 
	 * @param location
	 *            The location to create.
	 */
	public Location(Location location) {
		this.x = location.getX();
		this.y = location.getY();
		this.height = location.getHeight();
	}

	public Location copy() {
		return new Location(x, y, height);
	}
	
	public Location climbUp() {
	    return new Location(x, y, height + 1);
	}
	
	public Location climbDown() {
	    return new Location(x, y, height - 1);
	}

	/**
	 * Performs a check to see if the specified position is equal to the
	 * instanced one.
	 * 
	 * @param other
	 *            The other coordinate to be checked.
	 * 
	 * @return The result of the operation.
	 */
	public final boolean coordinatesEqual(Location other) {
		return other.getX() == this.getX() && other.getY() == this.getY() && other.getHeight() == this.getHeight();
	}

	@Override
	public boolean equals(Object other) {
		if (other == null || other.getClass() != getClass()) {
			return false;
		}

		final Location location = (Location) other;

		return coordinatesEqual(location);
	}

	/**
	 * Gets the delta {@code X} between this Location and an entity.
	 * 
	 * @return The delta {@code X} value.
	 */
	public int getDeltaX(Entity other) {
		return other.getX() - this.getX();
	}

	/**
	 * Gets the delta {@code Y} between this Location and an entity.
	 * 
	 * @return The delta {@code Y} value.
	 */
	public int getDeltaY(Entity other) {
		return other.getY() - this.getY();
	}

	/**
	 * Gets the {@code height level} on a plane.
	 * 
	 * @return The {@code height}.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Returns the local X coordinate of an {@link Location}.
	 * 
	 * @param location
	 *            The {@link Location}.
	 * 
	 * @return The returned local coordinate.
	 */
	public final int getLocalX(Location location) {
		return x - 8 * location.getRegionalX();
	}

	/**
	 * Returns the local Y coordinate of an {@link Location}.
	 * 
	 * @param location
	 *            The {@link Location}.
	 * 
	 * @return The returned local coordinate.
	 */
	public final int getLocalY(Location location) {
		return y - 8 * location.getRegionalY();
	}

	/**
	 * Returns the local X coordinate of thid {@link Location}.
	 * 
	 * @return The returned local coordinate.
	 */
	public final int getLocalX() {
		return x - 8 * getRegionalX();
	}

	/**
	 * Returns the local Y coordinate of this {@link Location}.
	 * 
	 * @return The returned local coordinate.
	 */
	public final int getLocalY() {
		return y - 8 * getRegionalY();
	}

	/**
	 * Returns the regional X coordinate.
	 * 
	 * @return The returned coordinate.
	 */
	public final int getRegionalX() {
		return (x >> 3) - 6;
	}

	/**
	 * Returns the regional Y coordinate.
	 * 
	 * @return The returned coordinate.
	 */
	public final int getRegionalY() {
		return (y >> 3) - 6;
	}

	/**
	 * Gets the {@code x} coordinate.
	 * 
	 * @return The {@code x} coordinate.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets a {@code y} coordinate.
	 * 
	 * @return The {@code y} coordinate.
	 */
	public int getY() {
		return y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, height);
	}

	/**
	 * Determines if another coordinate point is within a set distance of our
	 * own.
	 * 
	 * @param other
	 *            The coordinate to compare.
	 * 
	 * @param distance
	 *            The distance.
	 * 
	 * @return The result of the operation. <code> true </code> or
	 *         <code> false </code>.
	 */
	public final boolean isWithinDistance(Location other, int distance) {
		if (this.getHeight() != other.getHeight()) {
			return false;
		}
		return Math.abs(other.x - this.x) <= distance && Math.abs(other.y - this.y) <= distance;
	}
	
	/**
	 * Gets the Euclidean (straight-line) distance between two {@link Location}
	 * s.
	 * 
	 * @return The distance in tiles between the two locations.
	 */
	public static int getDistance(Location first, Location second) {
		final int dx = second.getX() - first.getX();
		final int dy = second.getY() - first.getY();
		return (int) Math.sqrt(dx * dx + dy * dy);
	}

	/**
	 * Gets the Euclidean (straight-line) distance between two {@link Location}
	 * s.
	 * 
	 * @return The distance in tiles between the two locations.
	 */
	public static int getManhattanDistance(Location first, Location second) {
		final int dx = Math.abs(second.getX() - first.getX());
		final int dy = Math.abs(second.getY() - first.getY());
		return dx + dy;
	}

	public boolean isWithinInteractionDistance(Location other) {
		if (height != other.height) {
			return false;
		}
		final int deltaX = other.x - x, deltaY = other.y - y;
		return deltaX <= 2 && deltaX >= -3 && deltaY <= 2 && deltaY >= -3;
	}

	/**
	 * Modifies the current location with the coordinates of another.
	 * 
	 * @param other
	 *            The new modification.
	 */
	public final void setLocation(Location other) {
		this.x = other.getX();
		this.y = other.getY();
		this.height = other.getHeight();
	}

	/**
	 * Adds an additional set of values onto the current position.
	 *
	 * @param x
	 *            The X coordinate value.
	 *
	 * @param y
	 *            The Y coordinate value.
	 *
	 * @param z
	 *            The height plane value.
	 */
	public final void move(int x, int y, int z) {
		this.x += x;
		this.y += y;
		this.height += z;
	}

	public Location transform(int x, int y, int h) {
		this.x += x;
		this.y += y;
		this.height += h;
		return this;
	}

	public Location add(Direction direction) {
		return new Location(x + direction.getDirectionX(), y + direction.getDirectionY(), height);
	}
	
	@Override
	public String toString() {
		return height > 0 ? String.format("[x= %d y= %d h= %d]", x, y, height) : String.format("[x= %d y= %d]", x, y);
	}

}