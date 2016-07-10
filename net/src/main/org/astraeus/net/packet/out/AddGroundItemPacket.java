package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.Sendable;

import java.util.Optional;

/**
 * The {@link OutgoingPacket} that displays items on the ground.
 * 
 * @author SeVen
 */
public final class AddGroundItemPacket implements Sendable {

	public AddGroundItemPacket(Object object) {
		//super(44);
//		this.item = item;
	}

	@Override
	public Optional<OutgoingPacket> writePacket(Player player) {
		//super(44);
		//synchronized (player) {
//			player.send(new SendCoordinate(item.getLocation()));
//			builder.writeShort(item.getItem().getId(), ByteModification.ADDITION, ByteOrder.LITTLE)
//			.writeShort(item.getItem().getAmount()).write(0);
			return Optional.empty();
		//}
	}

}
