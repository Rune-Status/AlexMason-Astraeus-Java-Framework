package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

/**
 * The {@link OutgoingPacket} responsible for changing settings on a client.
 * 
 * @author SeVen
 */
public final class SetWidgetConfigPacket extends OutgoingPacket {

	/**
	 * The configuration id.
	 */
	private final int id;

	/**
	 * The value to change.
	 */
	private final int value;

	/**
	 * Creates a new {@link SetWidgetConfigPacket}.
	 * 
	 * @param id
	 *            The configuration id.
	 * 
	 * @param value
	 *            The value to change.
	 */
	public SetWidgetConfigPacket(int id, int value) {
		super(36);
		this.id = id;
		this.value = value;
	}

	/**
	 * Creates a new {@link SetWidgetConfigPacket}.
	 * 
	 * @param id
	 *            The configuration id.
	 */
	public SetWidgetConfigPacket(int id, boolean enabled) {
		this(id, enabled ? 1 : 0);
	}

	@Override
	public GamePacketBuilder writePacket(Player player) {
		builder.writeShort(id, ByteOrder.LITTLE)
		.write(value);
		return builder;
	}

}
