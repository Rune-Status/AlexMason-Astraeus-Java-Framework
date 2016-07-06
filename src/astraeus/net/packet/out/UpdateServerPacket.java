package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.Sendable;

import java.util.Optional;

/**
 * The {@link OutgoingPacket} that sends a system update.
 * 
 * @author SeVen
 */
public final class UpdateServerPacket implements Sendable {

	/**
	 * The amount of seconds.
	 */
	private int seconds;

	/**
	 * Creates a new {@link UpdateServerPacket}.
	 * 
	 * @param seconds
	 *            The amount of seconds before a system update occurs.
	 */
	public UpdateServerPacket(int seconds) {
		this.seconds = seconds;
	}

	@Override
	public Optional<OutgoingPacket> writePacket(Player player) {
		GamePacketBuilder builder = new GamePacketBuilder(114);
		builder.writeShort(seconds * 50 / 30, ByteOrder.LITTLE);
		return builder.toOutgoingPacket();
	}

}
