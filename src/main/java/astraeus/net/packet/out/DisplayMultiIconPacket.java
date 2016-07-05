package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

public final class DisplayMultiIconPacket extends OutgoingPacket {

	private final boolean hide;

	public DisplayMultiIconPacket(boolean hide) {
		super(61);
		this.hide = hide;
	}

	@Override
	public GamePacketBuilder writePacket(Player player) {
		builder.write(hide ? 0 : 1);
		return builder;
	}

}
