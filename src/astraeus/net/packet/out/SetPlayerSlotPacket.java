package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.Sendable;

import java.util.Optional;

public final class SetPlayerSlotPacket implements Sendable {

	@Override
	public Optional<OutgoingPacket> writePacket(Player player) {

		GamePacketBuilder builder = new GamePacketBuilder(249);

		builder.write(1, ByteModification.ADDITION)
		.writeShort(player.getSlot(), ByteModification.ADDITION, ByteOrder.LITTLE);
		return builder.toOutgoingPacket();
	}

}
