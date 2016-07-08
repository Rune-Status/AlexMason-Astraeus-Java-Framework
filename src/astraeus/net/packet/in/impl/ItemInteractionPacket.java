package astraeus.net.packet.in.impl;

import astraeus.game.event.impl.ItemFirstClickEvent;
import astraeus.game.event.impl.ItemSecondClickEvent;
import astraeus.game.event.impl.ItemThirdClickEvent;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketReader;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.Receivable;

/**
 * The {@link IncomingPacket} responsible for clicking the actions of an
 * {@link Item}.
 * 
 * @author SeVen
 */
@IncomingPacket.IncomingPacketOpcode({ 16, 75, 122 })
public class ItemInteractionPacket implements Receivable {

	/**
	 * The opcode for an items first action.
	 */
	public static final int FIRST_ITEM_CLICK_OPCODE = 122;

	/**
	 * The opcode for an items second action.
	 */
	public static final int SECOND_ITEM_CLICK_OPCODE = 16;

	/**
	 * The opcode for an items third action.
	 */
	public static final int THIRD_ITEM_CLICK_OPCODE = 75;

	@Override
	public void handlePacket(Player player, IncomingPacket packet) {
		GamePacketReader reader = packet.getReader();
		
		if (player.isDead()) {
			return;
		}
		switch (packet.getOpcode()) {

		case FIRST_ITEM_CLICK_OPCODE:
			handleFirstAction(player, packet, reader);
			break;

		case SECOND_ITEM_CLICK_OPCODE:
			handleSecondAction(player, packet, reader);
			break;

		case THIRD_ITEM_CLICK_OPCODE:
			handleThirdAction(player, packet, reader);
			break;

		}
	}

	/**
	 * Handles the first option of an {@link Item}
	 * 
	 * @param player
	 *            The player clicking this item.
	 * 
	 * @param packet
	 *            The packet responsible for this action.
	 * 
	 * @param reader
	 *            The read-only buffer used to read the packet payload.
	 */
	private void handleFirstAction(Player player, IncomingPacket packet, GamePacketReader reader) {

		final int widgetId = reader.readShort(ByteOrder.LITTLE, ByteModification.ADDITION);
		final int slot = reader.readShort(false, ByteModification.ADDITION);
		final int id = reader.readShort(ByteOrder.LITTLE);

		player.post(new ItemFirstClickEvent(id, slot, widgetId));
	}

	/**
	 * Handles the second option of an {@link Item}
	 * 
	 * @param player
	 *            The player clicking this item.
	 *
	 * @param packet
	 *            The packet responsible for this action.
	 *
	 * @param reader
	 *            The read-only buffer used to read the packet payload.
	 */
	private void handleSecondAction(Player player, IncomingPacket packet, GamePacketReader reader) {
		final int itemId = reader.readShort(ByteModification.ADDITION);	

		player.post(new ItemSecondClickEvent(itemId, -1));
	}

	/**
	 * Handles the third option of an {@link Item}
	 * 
	 * @param player
	 *            The player clicking this item.
	 *
	 * @param packet
	 *            The packet responsible for this action.
	 *
	 * @param reader
	 *            The read-only buffer used to read the packet payload.
	 */
	private void handleThirdAction(Player player, IncomingPacket packet, GamePacketReader reader) {
		final int itemId11 = reader.readShort(ByteOrder.LITTLE);
		final int itemId1 = reader.readShort(ByteModification.ADDITION);	
		final int itemId = reader.readShort(ByteModification.ADDITION);	

		player.post(new ItemThirdClickEvent(itemId, itemId11, itemId1));
	}

}
