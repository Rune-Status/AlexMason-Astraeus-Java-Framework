package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.PacketHeader;

/**
 * The {@link OutgoingPacket} that sends a message to a {@link Player}s chatbox.
 * 
 * @author SeVen
 */
public final class ServerMessagePacket extends OutgoingPacket {

	/**
	 * The message to send.
	 */
	private final String message;

	/**
	 * Creates a new {@link ServerMessagePacket}.
	 */
	public ServerMessagePacket(String message) {
		super(253, PacketHeader.VARIABLE_BYTE);
		this.message = message;
	}

	@Override
	public GamePacketBuilder writePacket(Player player) {
		builder.writeString(message);
		return builder;
	}

}
