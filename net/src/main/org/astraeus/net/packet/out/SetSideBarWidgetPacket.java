package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.Sendable;

import java.util.Optional;

public final class SetSideBarWidgetPacket implements Sendable {

	private final int tabId;

	private final int interfaceId;

	public SetSideBarWidgetPacket(int tabId, int interfaceId) {
		this.tabId = tabId;
		this.interfaceId = interfaceId;
	}

	@Override
	public Optional<OutgoingPacket> writePacket(Player player) {
		GamePacketBuilder builder = new GamePacketBuilder(71);
		builder.writeShort(interfaceId)
		.write(tabId, ByteModification.ADDITION);
		return builder.toOutgoingPacket();
	}

}
