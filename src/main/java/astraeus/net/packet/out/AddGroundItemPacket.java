package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

/**
 * The {@link OutgoingPacket} that displays items on the ground.
 * 
 * @author SeVen
 */
public final class AddGroundItemPacket extends OutgoingPacket {

	public AddGroundItemPacket(Object object) {
		super(44);
//		this.item = item;
	}

	@Override
	public GamePacketBuilder writePacket(Player player) {
		//synchronized (player) {
//			player.send(new SendCoordinate(item.getLocation()));
//			builder.writeShort(item.getItem().getId(), ByteModification.ADDITION, ByteOrder.LITTLE)
//			.writeShort(item.getItem().getAmount()).write(0);
			return builder;
		//}
	}

}
