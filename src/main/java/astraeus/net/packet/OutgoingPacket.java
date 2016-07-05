package astraeus.net.packet;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.game.GamePacketBuilder;

/**
 * Represents an outgoing packet that can be sent to the client.
 * 
 * @author Seven
 */
public abstract class OutgoingPacket {

	/**
	 * The opcode of this packet.
	 */
	protected final int opcode;

	/**
	 * The header for this packet.
	 */
	protected final PacketHeader header;

	/**
	 * The builder to construct the packet.
	 */
	protected final GamePacketBuilder builder;

	/**
	 * Creates a new {@link OutgoingPacket} with a standard {@link PacketHeader}
	 * of {@code FIXED}.
	 * 
	 * @param opcode
	 *            The opcode of this packet.
	 */
	public OutgoingPacket(int opcode) {
		this(opcode, PacketHeader.FIXED);
	}

	/**
	 * Creates a new {@link OutgoingPacket}.
	 *
	 * @param opcode
	 * 		The id of this packet.
	 *
	 * @param header
	 * 		The header for this packet.
	 */
	public OutgoingPacket(int opcode, PacketHeader header) {
		this.opcode = opcode;
		this.header = header;
		this.builder = new GamePacketBuilder();
	}

	/**
	 * Packs the data into the buffer and prepares this packet to be sent through a {@link Player}s channel.
	 *
	 * @param player
	 * 		The player to send this packet for.
	 *
	 * 	@return The instance of this packet.
	 */
	public OutgoingPacket toPacket(Player player) {
		writePacket(player);
		return this;
	}

	/**
	 * Encodes the packets to binary.
	 *
	 * @param player
	 *            The player that is encoding this packet.
	 */
	public abstract GamePacketBuilder writePacket(Player player);

	/**
	 * Gets the header of this packet.
	 *
	 * @return The header.
	 */
	public PacketHeader getHeader() {
		return header;
	}

	/**
	 * Gets the opcode of this packet.
	 *
	 * @return The opcode.
	 */
	public int getOpcode() {
		return opcode;
	}

	/**
	 * Gets the buffer for this packet.
	 *
	 * @return The buffer.
	 */
	public GamePacketBuilder getBuilder() {
		return builder;
	}

	/**
	 * Gets the size of this packet.
	 *
	 * @return The packet size.
	 */
	public int getSize() {
		return builder.buffer().readableBytes();
	}

}
