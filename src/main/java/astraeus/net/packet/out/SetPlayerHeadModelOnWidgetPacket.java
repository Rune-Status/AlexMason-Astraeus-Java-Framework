package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

public final class SetPlayerHeadModelOnWidgetPacket extends OutgoingPacket {

	private final int interfaceId;

	public SetPlayerHeadModelOnWidgetPacket(int interfaceId) {
		super(185);
		this.interfaceId = interfaceId;
	}

	@Override
	public GamePacketBuilder writePacket(Player player) {
		builder.writeShort(interfaceId, ByteModification.ADDITION, ByteOrder.LITTLE);
		return builder;
	}

}
