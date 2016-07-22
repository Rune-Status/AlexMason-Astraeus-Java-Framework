package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.Sendable;

import java.util.Optional;

/**
 * The {@link OutgoingPacket} that displays an npc model on an interface.
 *
 * @author Seven
 */
public final class DisplayNpcHeadModelOnWidgetPacket implements Sendable {

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
		this.npcId = npcId;
		this.interfaceId = interfaceId;
	}

	@Override
	public Optional<OutgoingPacket> writePacket(Player player) {
		GamePacketBuilder builder = new GamePacketBuilder(75);
		builder.writeShort(npcId, ByteModification.ADDITION, ByteOrder.LITTLE).writeShort(interfaceId, ByteModification.ADDITION, ByteOrder.LITTLE);
		return builder.toOutgoingPacket();
	}

}
