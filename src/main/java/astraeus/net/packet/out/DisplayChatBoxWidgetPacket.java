package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

public final class DisplayChatBoxWidgetPacket extends OutgoingPacket {

	private final int interfaceId;

	public DisplayChatBoxWidgetPacket(int interfaceId) {
		super(164);
		this.interfaceId = interfaceId;
	}

	@Override
	public GamePacketBuilder writePacket(Player player) {
		builder.writeShort(interfaceId, ByteOrder.LITTLE);
		return builder;
	}

}
