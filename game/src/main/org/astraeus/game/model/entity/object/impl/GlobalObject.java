package astraeus.game.model.entity.object.impl;

import astraeus.game.model.Direction;
import astraeus.game.model.Location;

public final class GlobalObject {
	
	private final int id;
	
	private final String name;
	
	private final int type;
	
	private final Location location;
	
	private final Direction orientation;

	public GlobalObject(int id, String name, int type, Location location, Direction orientation) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.location = location;
		this.orientation = orientation;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getType() {
		return type;
	}

	public Location getLocation() {
		return location;
	}

	public Direction getOrientation() {
		return orientation;
	}
	
}
