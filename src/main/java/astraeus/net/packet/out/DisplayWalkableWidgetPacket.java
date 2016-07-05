package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

public final class DisplayWalkableWidgetPacket extends OutgoingPacket {

	private final int id;

	public DisplayWalkableWidgetPacket(int id) {
		super(208);
		this.id = id;
	}

	@Override
	public GamePacketBuilder writePacket(Player player) {
		builder.writeShort(id, ByteOrder.LITTLE);
		return builder;
	}

}
