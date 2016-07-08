package astraeus.net.packet.in.impl;

import astraeus.game.model.Location;
import astraeus.game.model.World;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.mob.player.PlayerRights;
import astraeus.game.model.entity.mob.player.attribute.Attribute;
import astraeus.game.model.entity.object.GameObject;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.in.IncomingPacketListener;
import astraeus.net.packet.out.ServerMessagePacket;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketReader;

/**
 * The {@link IncomingPacket}'s responsible for an items "use" option.
 * 
 * @author SeVen
 */
@IncomingPacket.IncomingPacketOpcode({ IncomingPacket.ITEM_ON_NPC, IncomingPacket.ITEM_ON_ITEM, IncomingPacket.ITEM_ON_OBJECT, IncomingPacket.ITEM_ON_GROUND_ITEM, IncomingPacket.ITEM_ON_PLAYER })
public class UseItemPacket implements IncomingPacketListener {

	@Override
	public void handlePacket(Player player, IncomingPacket packet) {
		switch (packet.getOpcode()) {

		case IncomingPacket.ITEM_ON_GROUND_ITEM:
			handleItemOnGround(player, packet);
			break;

		case IncomingPacket.ITEM_ON_ITEM:
			handleItemOnItem(player, packet);
			break;

		case IncomingPacket.ITEM_ON_NPC:
			handleItemOnNpc(player, packet);
			break;

		case IncomingPacket.ITEM_ON_OBJECT:
			handleItemOnObject(player, packet);
			break;

		case IncomingPacket.ITEM_ON_PLAYER:
			System.out.println("item on player packet received: " + packet.getOpcode());
			break;
		}
	}

	/**
	 * Handles an event when a player uses the "Use" option of an item with an
	 * item on the ground.
	 * 
	 * @param player
	 *            The player performing this action.
	 *
	 * @param packet
	 * 			The packet for this action.
	 */
	private void handleItemOnGround(Player player, IncomingPacket packet) {
		GamePacketReader reader = packet.getReader();

		final int a1 = reader.readShort(ByteOrder.LITTLE);
		final int itemUsed = reader.readShort(false, ByteModification.ADDITION);
		final int groundItem = reader.readShort();
		final int gItemY = reader.readShort(false, ByteModification.ADDITION);
		final int itemUsedSlot = reader.readShort(ByteOrder.LITTLE, ByteModification.ADDITION);
		final int gItemX = reader.readShort();

		if (player.getRights().equal(PlayerRights.DEVELOPER) && player.attr().contains(Attribute.DEBUG, true)) {
			player.send(new ServerMessagePacket("ItemUsed: " + itemUsed + " groundItem: " + groundItem + " itemUsedSlot: " + itemUsedSlot + " gItemX: " + gItemX + " gItemY: " + gItemY + " a1: " + a1));
		}
	}

	/**
	 * Handles an event when a player uses the "Use" option of an item with
	 * another item in a players inventory.
	 * 
	 * @param player
	 *            The player performing this action.
	 *
	 * @param packet
	 * 			The packet for this action.
	 */
	@SuppressWarnings("unused")
	private void handleItemOnItem(Player player, IncomingPacket packet) {
		GamePacketReader reader = packet.getReader();
		
		final int usedWithSlot = reader.readShort();
		final int itemUsedSlot = reader.readShort(ByteModification.ADDITION);

		//ItemOnItem.handleAction(player, used, with);
	}

	/**
	 * Handles an event when a player uses the "Use" option of an item with an
	 * in-game npc.
	 * 
	 * @param player
	 *          The player performing this action.
	 * 
	 * @param packet
	 * 			The packet for this action.
	 */
	private void handleItemOnNpc(Player player, IncomingPacket packet) {
		GamePacketReader reader = packet.getReader();
		
		@SuppressWarnings("unused")
		final int itemId = reader.readShort(false, ByteModification.ADDITION);
		final int npcSlot = reader.readShort(false, ByteModification.ADDITION);
		final int itemSlot = reader.readShort(ByteOrder.LITTLE);

		@SuppressWarnings("unused")
		final int npcId = World.WORLD.getMobs()[npcSlot].getId();

		if (!player.getInventory().contains(itemSlot)) {
			return;
		}

		if (World.WORLD.getMobs()[npcSlot] == null) {
			return;
		}

		//new ItemOnNpc(player, player.getInventory().getItem(itemSlot), new Npc(npcId, World.getMobs()[npcSlot].getSlot())).handleAction();
	}

	/**
	 * Handles an event when a player uses the "Use" option of an item with an
	 * in-game object.
	 * 
	 * @param player
	 *            The player performing this action.
	 * 
	 * @param packet
	 * 			The packet for this action.
	 */
	@SuppressWarnings("unused")
	private void handleItemOnObject(Player player, IncomingPacket packet) {
		GamePacketReader reader = packet.getReader();
		
		int interfaceType = reader.readShort();
		final int objectId = reader.readShort(ByteOrder.LITTLE);
		final int objectY = reader.readShort(ByteOrder.LITTLE, ByteModification.ADDITION);
		final int slot = reader.readShort(ByteOrder.LITTLE);
		final int objectX = reader.readShort(ByteOrder.LITTLE, ByteModification.ADDITION);
		final int itemId = reader.readShort();

		GameObject object = new GameObject(objectId, new Location(objectX, objectY));

		//ItemOnObject.handleAction(player, item, object, slot);
	}

}
