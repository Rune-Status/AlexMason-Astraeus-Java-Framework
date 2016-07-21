package astraeus.net.packet.out;

import astraeus.game.model.entity.item.Item;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.Sendable;

import java.util.Optional;

/**
 * The {@link Sendable} implementation that displays an item on the ground.
 * 
 * @author Vult-R
 */
public final class AddGroundItemPacket implements Sendable {

	private Item item;

	public AddGroundItemPacket(Item item) {
		this.item = item;
	}

	@Override
	public Optional<OutgoingPacket> writePacket(Player player) {
		player.send(new SetUpdateRegionPacket(player.getPosition()));
		
		final GamePacketBuilder builder = new GamePacketBuilder(44);
		builder.writeShort(item.getId(), ByteModification.ADDITION, ByteOrder.LITTLE)
		.writeShort(item.getAmount())
		.write(0); // offset
		return builder.toOutgoingPacket();
	}

}
