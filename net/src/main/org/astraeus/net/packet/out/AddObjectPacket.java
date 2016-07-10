package astraeus.net.packet.out;

import astraeus.game.model.Direction;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.object.GameObject;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.Sendable;

import java.util.Optional;

/**
 * The {@link OutgoingPacket} that creates an object for a user in the game
 * world.
 * 
 * @author SeVen
 */
public final class AddObjectPacket implements Sendable {

  /**
   * The object to spawn.
   */
  private final GameObject object;

  /**
   * The flag that denotes this is not a door.
   */
  private final boolean normal;

  /**
   * Creates a new {@link AddObjectPacket}.
   * 
   * @param object
   *            The object to spawn.
   *
   *  @param normal
   *      The flag that denotes this is not a door.
   */
  public AddObjectPacket(GameObject object, boolean normal) {
    this.object = object;
    this.normal = normal;
  }

  @Override
  public Optional<OutgoingPacket> writePacket(Player player) {
    GamePacketBuilder builder = new GamePacketBuilder(151);
    player.send(new SetUpdateRegionPacket(object.getLocation()));
    builder.write(object.getLocation().getHeight(), ByteModification.ADDITION)
    .writeShort(object.getId(), ByteOrder.LITTLE)
    .write(object.getType() << 2 | (normal ? object.getOrientation() : Direction.getDoorOrientation(object.getEnumeratedOrientation()) & 3), ByteModification.SUBTRACTION);
    return builder.toOutgoingPacket();
  }

}
