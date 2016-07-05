package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

/**
 * The {@link OutgoingPacket} that changes the settings/toggle on a client. This packet is
 * very similar to {@link SetWidgetConfigPacket} however supports integer values.
 * 
 * @author SeVen
 */
public final class SetToggleOnWidgetPacket extends OutgoingPacket {

	/**
	 * The configuration id.
	 */
	private final int id;

	/**
	 * The value to change.
	 */
	private final int value;	

	/**
	 * Creates a new {@link SendToggle).
	 * 
	 * @param id
	 * 		The configuration id.
	 * 
	 * @param value
	 * 		The value to change.
	 */
	public SetToggleOnWidgetPacket(int id, int value) {
		super(87);
		this.id = id;
		this.value = value;		
	}

	@Override
	public GamePacketBuilder writePacket(Player player) {
	    builder.writeShort(id, ByteOrder.LITTLE)
	    .writeInt(value, ByteOrder.MIDDLE);
	    return builder;
	}

}
