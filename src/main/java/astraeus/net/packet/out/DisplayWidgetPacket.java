package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

public final class DisplayWidgetPacket extends OutgoingPacket {

	private final int interfaceId;

	public DisplayWidgetPacket(int interfaceId) {
		super(97);
		this.interfaceId = interfaceId;
	}

	@Override
	public GamePacketBuilder writePacket(Player player) {
		builder.writeShort(interfaceId);
		return builder;
	}

}
