package astraeus.net.packet.in;

import astraeus.game.model.entity.item.Item;
import astraeus.game.model.entity.item.ItemDefinition;
import astraeus.game.model.entity.item.container.impl.Equipment;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.mob.player.PlayerRights;
import astraeus.game.model.entity.mob.player.attribute.Attribute;
import astraeus.game.model.entity.mob.update.UpdateFlag;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.game.GamePacketReader;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.Receivable;
import astraeus.net.packet.out.ServerMessagePacket;

/**
 * The {@link IncomingPacket} responsible for wielding items.
 * 
 * @author SeVen
 */
@IncomingPacket.IncomingPacketOpcode(41)
public class WieldItemPacket implements Receivable {

	@Override
	public void handlePacket(Player player, IncomingPacket packet) {
		GamePacketReader reader = packet.getReader();

		final int id = reader.readShort();		
		final int slot = reader.readShort(ByteModification.ADDITION);		
		final int interfaceId = reader.readShort(ByteModification.ADDITION);

		System.out.println("id: " + id + " slot: " + slot + " interfaceId: " + interfaceId);

		if (interfaceId == 3214) {

				if (!player.getInventory().contains(id)) {
					return;
				}

			int stackableAmount = 0;

			if (ItemDefinition.getDefinitions()[id].isTwoHanded() && player.getEquipment().getItems()[Equipment.SHIELD] != null) {
				if (player.getInventory().getFreeSlots() < 1) {
					player.send(new ServerMessagePacket("You don't have the required inventory space to equip this item."));
					return;
				}
			}

			if (ItemDefinition.getDefinitions()[id].isStackable()) {
				stackableAmount = player.getInventory().getItems()[slot].getAmount();
				player.getInventory().removeFromSlot(slot, player.getInventory().getItems()[slot].getAmount());
			} else {
				player.getInventory().removeFromSlot(slot, 1);
			}

			if (player.getEquipment().getItems()[ItemDefinition.lookup(id).getEquipmentSlot()] != null && ItemDefinition.lookup(id).isStackable()) {
				if (!player.getEquipment().contains(id)) {
					player.getInventory().set(slot, new Item(player.getEquipment().getItems()[ItemDefinition.getDefinitions()[id].getEquipmentSlot()].getId(), player.getEquipment().getItems()[ItemDefinition.getDefinitions()[id].getEquipmentSlot()].getAmount()));
				}
			} else if (player.getEquipment().getItems()[ItemDefinition.getDefinitions()[id].getEquipmentSlot()] != null){
				player.getInventory().set(slot, new Item(player.getEquipment().getItems()[ItemDefinition.getDefinitions()[id].getEquipmentSlot()].getId(), player.getEquipment().getItems()[ItemDefinition.getDefinitions()[id].getEquipmentSlot()].getAmount()));
			}

			if (ItemDefinition.getDefinitions()[id].isStackable()) {
				if (player.getEquipment().contains(id)) {
					player.getEquipment().getItems()[ItemDefinition.getDefinitions()[id].getEquipmentSlot()].setAmount(player.getEquipment().getItems()[ItemDefinition.getDefinitions()[id].getEquipmentSlot()].getAmount() + stackableAmount);
				} else {
					player.getEquipment().set(ItemDefinition.getDefinitions()[id].getEquipmentSlot(), new Item(id, stackableAmount));
				}

			} else {
				if (ItemDefinition.getDefinitions()[id].isTwoHanded() && player.getEquipment().getItems()[Equipment.SHIELD] != null) {
					player.getInventory().add(new Item(player.getEquipment().getItems()[Equipment.SHIELD].getId(), 1));
					player.getEquipment().removeFromSlot(Equipment.SHIELD,  1);
					player.getEquipment().refresh();
				} else if (player.getEquipment().getItems()[Equipment.WEAPON] != null && ItemDefinition.getDefinitions()[player.getEquipment().getItems()[Equipment.WEAPON].getId()].isTwoHanded() && ItemDefinition.getDefinitions()[id].getEquipmentSlot() == Equipment.SHIELD) {
					player.getInventory().add(new Item(player.getEquipment().getItems()[Equipment.WEAPON].getId(), 1));
					player.getEquipment().removeFromSlot(Equipment.WEAPON,  1);
					player.getEquipment().refresh();
				}
				player.getEquipment().set(ItemDefinition.getDefinitions()[id].getEquipmentSlot(), new Item(id, 1));
			}

			player.getEquipment().refresh();
			player.getEquipment().updateWeapon();
			player.getUpdateFlags().add(UpdateFlag.APPEARANCE);
		}

		if (player.getRights().greaterOrEqual(PlayerRights.DEVELOPER) && player.attr().contains(Attribute.DEBUG, true)) {
			player.send(new ServerMessagePacket(String.format("[WearItem] - [id= %d], [slot= %d], [interfaceId= %d]", id, slot, interfaceId)));
		}

	}
}
