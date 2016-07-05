package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

public final class DisplayInventoryWidgetPacket extends OutgoingPacket {

	private final int open;

	private final int overlay;

	public DisplayInventoryWidgetPacket(int open, int overlay) {
		super(248);
		this.open = open;
		this.overlay = overlay;
	}

	@Override
	public GamePacketBuilder writePacket(Player player) {
		builder.writeShort(open, ByteModification.ADDITION)
		.writeShort(overlay);
		return builder;
	}

}
