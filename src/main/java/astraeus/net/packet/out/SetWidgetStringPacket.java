package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.PacketHeader;

public final class SetWidgetStringPacket extends OutgoingPacket {

	private final String string;

	private final int id;

	public SetWidgetStringPacket(String string, int id) {
		super(126, PacketHeader.VARIABLE_SHORT);
		this.string = string;
		this.id = id;
	}

	@Override
	public GamePacketBuilder writePacket(Player player) {
		builder.writeString(string)
		.writeShort(id, ByteModification.ADDITION);
		return builder;
	}
}
