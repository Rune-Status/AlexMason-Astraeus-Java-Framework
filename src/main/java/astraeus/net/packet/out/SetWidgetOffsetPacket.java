package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

public final class SetWidgetOffsetPacket extends OutgoingPacket {

	private final int id;
	private final int x, y;

	public SetWidgetOffsetPacket(int x, int y, int id) {
		super(70);
		this.x = x;
		this.y = y;
		this.id = id;
	}

	@Override
	public GamePacketBuilder writePacket(Player player) {
		builder.writeShort(x)
		.writeShort(y, ByteOrder.LITTLE)
		.writeShort(id, ByteOrder.LITTLE);
		return builder;
	}

}
