package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.PacketHeader;
import astraeus.net.packet.Sendable;

import java.util.Optional;

public final class SetWidgetStringPacket implements Sendable {

	private final String string;

	private final int id;

	public SetWidgetStringPacket(String string, int id) {
		this.string = string;
		this.id = id;
	}

	@Override
	public Optional<OutgoingPacket> writePacket(Player player) {
		GamePacketBuilder builder = new GamePacketBuilder(126, PacketHeader.VARIABLE_SHORT);
		builder.writeString(string)
		.writeShort(id, ByteModification.ADDITION);
		return builder.toOutgoingPacket();
	}
}
