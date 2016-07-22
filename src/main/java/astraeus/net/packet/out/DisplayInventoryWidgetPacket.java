package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.Sendable;

import java.util.Optional;

public final class DisplayInventoryWidgetPacket implements Sendable {

	private final int open;

	private final int overlay;

	public DisplayInventoryWidgetPacket(int open, int overlay) {
		this.open = open;
		this.overlay = overlay;
	}

	@Override
	public Optional<OutgoingPacket> writePacket(Player player) {
		GamePacketBuilder builder = new GamePacketBuilder(248);
		builder.writeShort(open, ByteModification.ADDITION)
		.writeShort(overlay);
		return builder.toOutgoingPacket();
	}

}
