package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.Sendable;

import java.util.Optional;

public final class DisplayPlayerHeadModelOnWidgetPacket implements Sendable {

	private final int interfaceId;

	public DisplayPlayerHeadModelOnWidgetPacket(int interfaceId) {
		this.interfaceId = interfaceId;
	}

	@Override
	public Optional<OutgoingPacket> writePacket(Player player) {
		GamePacketBuilder builder = new GamePacketBuilder(185);
		builder.writeShort(interfaceId, ByteModification.ADDITION, ByteOrder.LITTLE);
		return builder.toOutgoingPacket();
	}

}
