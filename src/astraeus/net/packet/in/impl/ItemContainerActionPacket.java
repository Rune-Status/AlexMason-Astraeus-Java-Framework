package astraeus.net.packet.in.impl;

import astraeus.game.model.entity.item.Item;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.mob.player.PlayerRights;
import astraeus.game.model.entity.mob.player.attribute.Attribute;
import astraeus.game.model.entity.mob.update.UpdateFlag;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.ByteOrder;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.Receivable;
import astraeus.net.packet.IncomingPacket.IncomingPacketOpcode;
import astraeus.net.codec.game.GamePacketReader;
import astraeus.net.packet.out.ServerMessagePacket;

/**
 * The {@link IncomingPacket}'s responsible for the options for Items inside a
 * container interface.
 * 
 * Item containers include: - Banks - Shops - Trade - Duel And any other
 * container where items are held within an interface.
 * 
 * @author Seven
 */
@IncomingPacketOpcode({ 145, 117, 43, 129, 135, 208 })
public class ItemContainerActionPacket implements Receivable {

	@Override
	public void handlePacket(Player player, IncomingPacket packet) {
		GamePacketReader reader = packet.getReader();
		switch (packet.getOpcode()) {
		case FIRST_ITEM_ACTION_OPCODE:
			firstAction(player, packet, reader);
			break;

		case SECOND_ITEM_ACTION_OPCODE:
			secondAction(player, packet, reader);
			break;

		case THIRD_ITEM_ACTION_OPCODE:
			thirdAction(player, packet, reader);
			break;

		case FOURTH_ITEM_ACTION_OPCODE:
			fourthAction(player, packet, reader);
			break;

		case FIFTH_ITEM_ACTION_OPCODE:
			fifthAction(player, packet);
			break;

		case SIXTH_ITEM_ACTION_OPCODE:
			sixthAction(player, packet);
			break;
		}
	}

	/**
	 * Handles the event when a player clicks on the first option of an item
	 * container interface.
	 * 
	 * @param player
	 *            The player clicking the option.
	 * 
	 * @param packet
	 *            The packet responsible for this action.
	 */
	private void firstAction(Player player, IncomingPacket packet, GamePacketReader reader) {
		
		final int interfaceId = reader.readShort(ByteModification.ADDITION);		
		final int itemSlot = reader.readShort(ByteModification.ADDITION);
		final int itemId = reader.readShort(ByteModification.ADDITION);

		if (player.attr().contains(Attribute.DEBUG, true) && player.getRights().equals(PlayerRights.DEVELOPER)) {
			player.send(new ServerMessagePacket("[ItemContainerAction] - FirstAction - InterfaceId: " + interfaceId + " (" + itemId + ", " + itemSlot + ")"));
		}

		switch (interfaceId) {

			case 1688:
				player.getInventory().add(new Item(itemId, player.getEquipment().getItem(itemSlot).getAmount()));
				player.getEquipment().removeFromSlot(itemSlot,  player.getEquipment().getItem(itemSlot).getAmount());
				player.getEquipment().refresh();
				player.getEquipment().updateWeapon();
				player.getUpdateFlags().add(UpdateFlag.APPEARANCE);
				break;

			case 5064:
				player.getBank().depositItem(new Item(itemId, 1), itemSlot);
				break;

			case 5382:
				player.getBank().withdrawItem(1, itemSlot);
				break;

		}

	}

	/**
	 * Handles the event when a player clicks on the second option of an item
	 * container interface.
	 * 
	 * @param player
	 *            The player clicking the option.
	 *
	 * @param packet
	 *            The packet responsible for this action.
	 */
	private void secondAction(Player player, IncomingPacket packet, GamePacketReader reader) {
		final int interfaceId = reader.readShort(ByteOrder.LITTLE, ByteModification.ADDITION);
		final int itemId = reader.readShort(ByteOrder.LITTLE, ByteModification.ADDITION);
		final int itemSlot = reader.readShort(ByteOrder.LITTLE);

		if (player.getRights().equal(PlayerRights.DEVELOPER) && player.attr().contains(Attribute.DEBUG, true)) {
			player.send(new ServerMessagePacket("[ItemContainerAction] - SecondClick - InterfaceId: " + interfaceId + " itemId: " + itemId + " slot: " + itemSlot));
		}

		switch (interfaceId) {

			case 5064:
				player.getBank().depositItem(new Item(itemId, 5), itemSlot);
				break;

			case 5382:
				player.getBank().withdrawItem(5, itemSlot);
				break;

		}
	}

	/**
	 * Handles the event when a player clicks on the third option of an item
	 * container interface.
	 * 
	 * @param player
	 *            The player clicking the option.
	 *
	 * @param packet
	 *            The packet responsible for this action.
	 */
	private void thirdAction(Player player, IncomingPacket packet, GamePacketReader reader) {
		final int interfaceId = reader.readShort(ByteOrder.LITTLE);
		final int itemId = reader.readShort(ByteModification.ADDITION);
		final int itemSlot = reader.readShort(ByteModification.ADDITION);

		if (player.getRights().equal(PlayerRights.DEVELOPER) && player.attr().contains(Attribute.DEBUG, true)) {
			player.send(new ServerMessagePacket("[ItemContainerAction] - ThirdClick - InterfaceId: " + interfaceId + " itemId: " + itemId + " slot: " + itemSlot));
		}

		switch (interfaceId) {

			case 5064:
				player.getBank().depositItem(new Item(itemId, 10), itemSlot);
				break;

			case 5382:
				player.getBank().withdrawItem(10, itemSlot);
				break;

		}
	}

	/**
	 * Handles the event when a player clicks on the fourth option of an item
	 * container interface.
	 * 
	 * @param player
	 *            The player clicking the option.
	 *
	 * @param packet
	 *            The packet responsible for this action.
	 */
	private void fourthAction(Player player, IncomingPacket packet, GamePacketReader reader) {
		final int itemSlot = reader.readShort(ByteModification.ADDITION);
		final int interfaceId = reader.readShort();
		final int itemId = reader.readShort(ByteModification.ADDITION);

		if (player.getRights().equal(PlayerRights.DEVELOPER) && player.attr().contains(Attribute.DEBUG, true)) {
			player.send(new ServerMessagePacket("[ItemContainerAction] - FourthAction - InterfaceId: " + interfaceId + " itemId: " + itemId + " slot: " + itemSlot));
		}

		switch (interfaceId) {

			case 5064:
				player.getBank().depositItem(new Item(itemId, player.getInventory().getItemAmount(itemId)), itemSlot);
				break;

			case 5382:
				player.getBank().withdrawItem(player.getBank().getItemAmount(itemId), itemSlot);
				break;

		}
	}

	/**
	 * Handles the event when a player clicks on the fifth option of an item
	 * container interface.
	 * 
	 * @param player
	 *            The player clicking the option.
	 *
	 * @param packet
	 *            The packet responsible for this action.
	 */
	@SuppressWarnings("unused")
      private void fifthAction(Player player, IncomingPacket packet) {
		GamePacketReader reader = packet.getReader();
		
		int itemSlot = reader.readShort(ByteOrder.LITTLE);
		int interfaceId = reader.readShort(ByteModification.ADDITION);		
		int itemId = reader.readShort(ByteOrder.LITTLE);
	}

	/**
	 * Handles the event when a player clicks on the sixth option of an item
	 * container interface.
	 * 
	 * @param player
	 *            The player clicking the option.
	 * 
	 * @param packet
	 *            The packet responsible for this action.
	 */
	private void sixthAction(Player player, IncomingPacket packet) {
		int amountX = packet.getReader().readInt();

		if (amountX == 0) {
			amountX = 1;
		}

	}

	/**
	 * The first option opcode of an item inside a container interface.
	 */
	public static final int FIRST_ITEM_ACTION_OPCODE = 145;
	/**
	 * The second option opcode of an item inside a container interface.
	 */
	public static final int SECOND_ITEM_ACTION_OPCODE = 117;
	/**
	 * The third option opcode of an item inside a container interface.
	 */
	public static final int THIRD_ITEM_ACTION_OPCODE = 43;
	/**
	 * The fourth option opcode of an item inside a container interface.
	 */
	public static final int FOURTH_ITEM_ACTION_OPCODE = 129;
	/**
	 * The fifth option opcode of an item inside a container interface.
	 */
	public static final int FIFTH_ITEM_ACTION_OPCODE = 135;
	/**
	 * The sixth option opcode of an item inside a container interface.
	 */
	public static final int SIXTH_ITEM_ACTION_OPCODE = 208;

}
