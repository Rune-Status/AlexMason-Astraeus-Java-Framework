package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

/**
 * The {@link OutgoingPacket} that sends a system update.
 * 
 * @author SeVen
 */
public final class UpdateServerPacket extends OutgoingPacket {

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
		super(114);
		this.seconds = seconds;
	}

	@Override
	public GamePacketBuilder writePacket(Player player) {
		builder.writeShort(seconds * 50 / 30, ByteOrder.LITTLE);
		return builder;
	}

}
