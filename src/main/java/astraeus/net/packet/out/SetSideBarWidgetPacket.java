package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

public final class SetSideBarWidgetPacket extends OutgoingPacket {

	private final int tabId;

	private final int interfaceId;

	public SetSideBarWidgetPacket(int tabId, int interfaceId) {
		super(71);
		this.tabId = tabId;
		this.interfaceId = interfaceId;
	}

	@Override
	public GamePacketBuilder writePacket(Player player) {
		builder.writeShort(interfaceId)
		.write(tabId, ByteModification.ADDITION);
		return builder;
	}

}
