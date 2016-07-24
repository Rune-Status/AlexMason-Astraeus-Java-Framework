package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.Sendable;

import java.util.Optional;

public final class DisplayInventoryWidgetPacket implements Sendable {

	private final int widgetId;	

	private final int sidebarId;	

	public DisplayInventoryWidgetPacket(int widgetId, int sidebarId) {	
		
		this.widgetId = widgetId;
		this.sidebarId = sidebarId;
	}

	@Override
	public Optional<OutgoingPacket> writePacket(Player player) {
		GamePacketBuilder builder = new GamePacketBuilder(248);
		builder.writeShort(widgetId, ByteModification.ADDITION)
		.writeShort(sidebarId);
		return builder.toOutgoingPacket();
	}

}
