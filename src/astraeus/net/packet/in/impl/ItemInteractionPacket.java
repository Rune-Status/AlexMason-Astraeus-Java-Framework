package astraeus.net.packet.in.impl;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.mob.player.PlayerRights;
import astraeus.game.model.entity.mob.player.attribute.Attribute;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketReader;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.in.IncomingPacketListener;
import astraeus.net.packet.out.ServerMessagePacket;

/**
 * The {@link IncomingPacket} responsible for clicking the actions of an
 * {@link Item}.
 * 
 * @author SeVen
 */
@IncomingPacket.IncomingPacketOpcode({ 16, 75, 122 })
public class ItemInteractionPacket implements IncomingPacketListener {

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
	@SuppressWarnings("unused")
	private void handleFirstAction(Player player, IncomingPacket packet, GamePacketReader reader) {

		final int interfaceId = reader.readShort(ByteOrder.LITTLE, ByteModification.ADDITION);
		final int slot = reader.readShort(false, ByteModification.ADDITION);
		final int id = reader.readShort(ByteOrder.LITTLE);

		switch (interfaceId) {

		}
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

		if (player.getRights().equal(PlayerRights.DEVELOPER) && player.attr().contains(Attribute.DEBUG, true)) {
			player.send(new ServerMessagePacket("ItemAction - 2: itemId - " + itemId));
		}

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

		if (player.getRights().equal(PlayerRights.DEVELOPER) && player.attr().contains(Attribute.DEBUG, true)) {
			player.send(new ServerMessagePacket("ItemAction - 3: itemId - " + itemId + " itemId1: " + itemId1 + " itemId11: " + itemId11));
		}
	}

}
