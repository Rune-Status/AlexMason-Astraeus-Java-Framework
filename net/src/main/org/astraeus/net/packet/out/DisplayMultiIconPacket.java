package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.Sendable;

import java.util.Optional;

public final class DisplayMultiIconPacket implements Sendable {

	private final boolean hide;

	public DisplayMultiIconPacket(boolean hide) {
		this.hide = hide;
	}

	@Override
	public Optional<OutgoingPacket> writePacket(Player player) {
		GamePacketBuilder builder = new GamePacketBuilder(61);
		builder.write(hide ? 0 : 1);
		return builder.toOutgoingPacket();
	}

}
