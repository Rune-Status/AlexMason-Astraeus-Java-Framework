package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.Sendable;

import java.util.Optional;

public final class DisplayWidgetPacket implements Sendable {

	private final int interfaceId;

	public DisplayWidgetPacket(int interfaceId) {
		this.interfaceId = interfaceId;
	}

	@Override
	public Optional<OutgoingPacket> writePacket(Player player) {
		GamePacketBuilder builder = new GamePacketBuilder(97);
		builder.writeShort(interfaceId);
		return builder.toOutgoingPacket();
	}

}
