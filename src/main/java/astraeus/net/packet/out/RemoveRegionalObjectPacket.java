package astraeus.net.packet.out;

import astraeus.game.model.Direction;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.object.GameObject;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

/**
 * The {@link OutgoingPacket} implementation that removes an object from a users client.
 *
 * @author Seven
 */
public final class RemoveRegionalObjectPacket extends OutgoingPacket {

	/**
	 * The {@code object} that is being removed.
	 */
	private final GameObject object;

	private final boolean normal;

	/**
	 * Creates a new {@link RemoveRegionalObjectPacket} packet.
	 *
	 * @param object The object to remove.
	 */
	public RemoveRegionalObjectPacket(GameObject object, boolean normal) {
		super(101);
		this.object = object;
		this.normal = normal;
	}

	@Override
	public GamePacketBuilder writePacket(Player player) {
		player.send(new SetUpdateRegionPacket(object.getLocation()));
		builder.write(object.getType() << 2 | (normal ? object.getOrientation() : Direction.getDoorOrientation(object.getEnumeratedOrientation()) & 3), ByteModification.NEGATION)
				.write(0);
		return builder;
	}

}
