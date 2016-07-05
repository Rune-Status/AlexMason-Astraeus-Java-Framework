package astraeus.game.model.entity.mob.npc;

import astraeus.game.model.Direction;
import astraeus.game.model.Location;

/**
 * A class which represents a single Npc Spawn.
 * 
 * @author Seven
 */
public final class NpcSpawn {

    /**
     * The id of this mob.
     */
    private final int id;

    /**
     * The position of this npc.
     */
    private final Location location;

    /**
     * The ability of this mob to walk in random directions.
     */
    private final boolean randomWalk;

    /**
     * The facing direction of this npc.
     */
    private final Direction facing;
    
    public NpcSpawn(int id, Location location) {
	this(id, location, true, Direction.SOUTH);
    }
    
    public NpcSpawn(int id, Location location, Direction facing) {
	this(id, location, true, facing);
    }

    public NpcSpawn(int id, Location location, boolean randomWalk, Direction facing) {
	this.id = id;
	this.location = location;
	this.randomWalk = randomWalk;
	this.facing = facing;
    }

    /**
     * Gets the facing direction of a {@link Npc}.
     *
     * @return The facing direction.
     */
    public Direction getFacing() {
	return facing;
    }

    /**
     * Gets the id of this {@link Npc}.
     *
     * @return The id of the npc.
     */
    public int getId() {
	return id;
    }

    /**
     * Gets the location of the {@link Npc}.
     *
     * @return The location of the npc.
     */
    public Location getLocation() {
	return location;
    }

    /**
     * Determines if this {@Link Npc} will randomly wakl.
     *
     * @return {@code true} If this npc will randomly walk. {@code false} otherwise.
     */
    public boolean isRandomWalk() {
	return randomWalk;
    }

    @Override
    public String toString() {
	return "Id: " + id + " pos: " + getLocation().toString() + " randomWalk: " + randomWalk + " facing: "
		+ facing.name();
    }

}