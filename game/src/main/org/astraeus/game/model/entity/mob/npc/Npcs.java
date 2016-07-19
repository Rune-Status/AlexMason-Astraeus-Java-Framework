package astraeus.game.model.entity.mob.npc;

import astraeus.game.GameConstants;
import astraeus.game.model.Position;
import astraeus.game.model.World;
import astraeus.game.model.entity.mob.update.UpdateFlag;
import astraeus.util.IntUtils;
import astraeus.util.RandomUtils;

import java.util.concurrent.TimeUnit;

/**
 * A static-utility class that contains methods for mobs.
 * 
 * @author SeVen
 */
public class Npcs {

	/**
	 * Spawns a {@link Npc} into the game world.
	 * 
	 * @param spawn
	 *            The mob to spawn.
	 */
	public static void createSpawn(NpcSpawn spawn) {
		final int slot = IntUtils.findFreeIndex(World.WORLD.getMobs());

		if (slot == -1) {
			return;
		}

		final Npc mob = new Npc(spawn.getId(), slot);
		mob.setLocation(spawn.getPosition());
		mob.setCreatedLocation(new Position(spawn.getPosition()));

		mob.setFacingDirection(spawn.getFacing());
		mob.setRandomWalk(spawn.isRandomWalk());
		mob.setRegistered(true);
		mob.setVisible(true);
		mob.getUpdateFlags().add(UpdateFlag.APPEARANCE);
		World.WORLD.getMobs()[slot] = mob;
	}

	/**
	 * Handles randomly walking movement for a mob.
	 * 
	 * @param mob
	 *            The mob to handle.
	 */
	public static void handleRandomWalk(Npc mob) {
		if (mob.getRandomWalkTimer().elapsed(TimeUnit.SECONDS) >= 1) {

			if ((RandomUtils.random(5)) == 1) {

				int randomX = RandomUtils.random(-1, 1);
				int randomY = RandomUtils.random(-1, 1);

				Position nextLocation = new Position(mob.getPosition().getX() + randomX, mob.getPosition().getY() + randomY, mob.getPosition().getHeight());

				int distance = Position.getDistance(mob.getCreatedLocation(), nextLocation);

				if (mob.getInteractingEntity() == null && distance <= GameConstants.NPC_RANDOM_WALK_DISTANCE) {
					mob.getMovement().walk(nextLocation);
				} else if (mob.getInteractingEntity() == null) {
					mob.getMovement().walk(mob.getCreatedLocation());
				}
				mob.getRandomWalkTimer().reset();
			}
		}
	}

	/**
	 * Resets the facing direction of an entity to its default direction.
	 * 
	 * @param mob
	 *            The mob to reset.
	 */
	public static void resetFacingDirection(Npc mob) {
		if (mob.isRandomWalk() && mob.getInteractingEntity() == null) {
			return;
		}

		mob.setFacingDirection(mob.getFacingDirection());
	}

	public static boolean isDragon(Npc mob) {
		if (mob == null) {
			return false;
		}
		
		NpcDefinition def = NpcDefinition.get(mob.getId());
		
		if (def == null || def.getName() == null) {
			return false;
		}

		return def.getName().toLowerCase().contains("dragon");
	}

}
