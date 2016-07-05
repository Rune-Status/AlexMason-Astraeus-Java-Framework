package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.Sendable;

import java.util.Optional;

public final class UpdateMapRegion implements Sendable {

	@Override
	public Optional<OutgoingPacket> writePacket(Player player) {
		GamePacketBuilder builder = new GamePacketBuilder(73);
		player.getLastLocation().setLocation(player.getLocation());
		builder.writeShort(player.getLocation().getRegionalX() + 6, ByteModification.ADDITION)
		.writeShort(player.getLocation().getRegionalY() + 6);
		return builder.toOutgoingPacket();
	}

}
