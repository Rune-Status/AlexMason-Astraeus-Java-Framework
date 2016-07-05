package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

public final class SetPlayerSlotPacket extends OutgoingPacket {

	public SetPlayerSlotPacket() {
		super(249);
	}

	@Override
	public GamePacketBuilder writePacket(Player player) {
		builder.write(1, ByteModification.ADDITION)
		.writeShort(player.getSlot(), ByteModification.ADDITION, ByteOrder.LITTLE);
		return builder;
	}

}
