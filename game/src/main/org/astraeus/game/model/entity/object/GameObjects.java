package astraeus.game.model.entity.object;

import astraeus.game.model.Position;
import astraeus.game.model.entity.item.Item;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.packet.out.AddGroundItemPacket;
import astraeus.net.packet.out.AddObjectPacket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The class that provides static utility methods for {@link GameObject}s.
 *
 * @author Seven
 */
public final class GameObjects {

	/**
	 * The list of global object spawned in the game world.
	 */
	private static final List<GameObject> GLOBAL_OBJECTS = new ArrayList<>();

	/**
	 * A map of ground items and their positions in the world.
	 */
	private static final Map<Position, Item> GROUND_ITEMS = new HashMap<>();

	/**
	 * The method that creates global objects for a user.
	 * 
	 * @param player
	 *            The player to create the global objects for.
	 */
	public static final void createGlobalObjects(Player player) {
		GLOBAL_OBJECTS.stream().filter(Objects::nonNull)
				.filter($it -> $it.getPosition().isWithinDistance(player.getPosition(), 32))
				.forEach($it -> player.send(new AddObjectPacket($it, true)));
	}
	
	public static final void createGlobalItems(Player player) {
		GROUND_ITEMS.values().stream().filter(Objects::nonNull).filter($it -> $it.getPosition().isWithinDistance(player.getPosition(), 32)).forEach($it -> player.send(new AddGroundItemPacket($it)));
	}

	/**
	 * Gets the custom object spawns.
	 * 
	 * @return The custom objects.
	 */
	public static final List<GameObject> getGlobalObjects() {
		return GLOBAL_OBJECTS;
	}

	public static Map<Position, Item> getGroundItems() {
		return GROUND_ITEMS;
	}

}
