package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.PacketHeader;
import astraeus.net.packet.Sendable;

import java.util.Optional;

/**
 * The {@link OutgoingPacket} that sends a message to a {@link Player}s chatbox.
 * 
 * @author SeVen
 */
public final class ServerMessagePacket implements Sendable {

	/**
	 * The message to send.
	 */
	private final String message;

	/**
	 * Creates a new {@link ServerMessagePacket}.
	 */
	public ServerMessagePacket(String message) {
		this.message = message;
	}

	@Override
	public Optional<OutgoingPacket> writePacket(Player player) {
		GamePacketBuilder builder = new GamePacketBuilder(253, PacketHeader.VARIABLE_BYTE);
		builder.writeString(message);
		return builder.toOutgoingPacket();
	}

}
