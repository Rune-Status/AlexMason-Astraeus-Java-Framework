package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

/**
 * The {@link OutgoingPacket} that displays an npc model on an interface.
 *
 * @author Seven
 */
public final class DisplayNpcHeadModelOnWidgetPacket extends OutgoingPacket {

	/**
	 * The id of the npc to display.
	 */
	private final int npcId;

	/**
	 * The id of the interface to display on.
	 */
	private final int interfaceId;

	/**
	 * Creates a new {@link DisplayNpcHeadModelOnWidgetPacket}.
	 *
	 * @param npcId
	 * 		The id of the npc to show.
	 *
	 * 	@param interfaceId
	 * 		The id of the interface to display on.
	 */
	public DisplayNpcHeadModelOnWidgetPacket(int npcId, int interfaceId) {
		super(75);
		this.npcId = npcId;
		this.interfaceId = interfaceId;
	}

	@Override
	public GamePacketBuilder writePacket(Player player) {
		builder.writeShort(npcId, ByteModification.ADDITION, ByteOrder.LITTLE).writeShort(interfaceId, ByteModification.ADDITION, ByteOrder.LITTLE);
		return builder;
	}

}
