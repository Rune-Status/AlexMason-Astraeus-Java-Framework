package astraeus.game.model.entity.object;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.object.impl.Door;
import astraeus.net.packet.out.AddObjectPacket;

import java.util.ArrayList;
import java.util.List;
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
   * The list of door objects spawned in the game world.
   */
  private static final List<Door> ACTIVE_DOORS = new ArrayList<>();

  /**
   * The method that creates global objects for a user.
   * 
   * @param player
   *            The player to create the global objects for.
   */
  public static final void createGlobalObjects(Player player) {
    GLOBAL_OBJECTS.stream().filter(Objects::nonNull).filter($it -> $it.getLocation().isWithinDistance(player.getLocation(), 32)).forEach( $it -> player.send(new AddObjectPacket($it, true)));
  }

  /**
   * @return the doors
   */
  public static final List<Door> getDoors() {
    return ACTIVE_DOORS;
  }

  /**
   * Gets the custom object spawns.
   * 
   * @return The custom objects.
   */
  public static final List<GameObject> getGlobalObjects() {
    return GLOBAL_OBJECTS;
  }

}
