package astraeus.net.packet.in;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketReader;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.Receivable;

/**
 * The {@link IncomingPacket} responsible for moving player items in inventory.
 * 
 * @author SeVen
 */
@IncomingPacket.IncomingPacketOpcode(IncomingPacket.MOVE_ITEM)
public class MoveItemPacket implements Receivable {

	@Override
	public void handlePacket(Player player, IncomingPacket packet) {
		GamePacketReader reader = packet.getReader();

		final int interfaceId = reader.readShort(ByteOrder.LITTLE, ByteModification.ADDITION);
		@SuppressWarnings("unused")
		final int inserting = reader.readByte(ByteModification.NEGATION);
		final int fromSlot = reader.readShort(ByteOrder.LITTLE, ByteModification.ADDITION);
		final int toSlot = reader.readShort(ByteOrder.LITTLE);

		switch (interfaceId) {

			case 3214:
				player.getInventory().swapItems(fromSlot, toSlot);
				break;

			case 5382:

				break;

		default:
			System.out.println("Unkown Item movement interface id: " + interfaceId);
			break;
		}
	}
}
