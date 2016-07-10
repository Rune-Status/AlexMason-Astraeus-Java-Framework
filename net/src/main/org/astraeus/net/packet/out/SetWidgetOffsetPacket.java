package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.Sendable;

import java.util.Optional;

public final class SetWidgetOffsetPacket implements Sendable {

	private final int id;
	private final int x, y;

	public SetWidgetOffsetPacket(int x, int y, int id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}

	@Override
	public Optional<OutgoingPacket> writePacket(Player player) {
		GamePacketBuilder builder = new GamePacketBuilder(70);
		builder.writeShort(x)
		.writeShort(y, ByteOrder.LITTLE)
		.writeShort(id, ByteOrder.LITTLE);
		return builder.toOutgoingPacket();
	}

}
