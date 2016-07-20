package astraeus.net.packet.in;

import astraeus.game.model.entity.item.Item;
import astraeus.game.model.entity.item.ItemDefinition;
import astraeus.game.model.entity.mob.player.Equipment;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.mob.player.PlayerRights;
import astraeus.game.model.entity.mob.player.Equipment.EquipmentDefinition;
import astraeus.game.model.entity.mob.player.attribute.Attribute;
import astraeus.game.model.entity.mob.update.UpdateFlag;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.game.GamePacketReader;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.Receivable;
import astraeus.net.packet.out.ServerMessagePacket;

/**
 * The {@link IncomingPacket} responsible for equipping items.
 * 
 * @author SeVen
 */
@IncomingPacket.IncomingPacketOpcode(IncomingPacket.EQUIP_ITEM)
public final class EquipItemPacket implements Receivable {

	@Override
	public void handlePacket(Player player, IncomingPacket packet) {
		GamePacketReader reader = packet.getReader();

		final int id = reader.readShort();
		final int slot = reader.readShort(ByteModification.ADDITION);
		final int interfaceId = reader.readShort(ByteModification.ADDITION);

		if (player.getRights().greaterOrEqual(PlayerRights.DEVELOPER)
				&& player.attr().contains(Attribute.DEBUG, true)) {
			player.send(new ServerMessagePacket(
					String.format("[EquipItem] - [id= %d], [slot= %d], [interfaceId= %d]", id, slot, interfaceId)));
		}

		if (interfaceId == 3214) {

			if (!player.getInventory().contains(id)) {
				return;
			}

			int stackableAmount = 0;

			EquipmentDefinition equipDef = EquipmentDefinition.get(id);

			if (equipDef == null) {
				return;
			}

			// check if shield can be removed
			if (equipDef.isTwoHanded() && player.getEquipment().getItems()[Equipment.SHIELD] != null) {
				if (player.getInventory().getFreeSlots() < 1) {
					player.send(new ServerMessagePacket("You don't have the required inventory space to equip this item."));
					return;
				}
			}

			ItemDefinition itemDef = ItemDefinition.lookup(id);

			if (itemDef == null) {
				return;
			}

			// handle un-equipping an item
			if (itemDef.isStackable()) {
				stackableAmount = player.getInventory().getItems()[slot].getAmount();
				player.getInventory().removeFromSlot(slot, player.getInventory().getItems()[slot].getAmount());
			} else {
				player.getInventory().removeFromSlot(slot, 1);
			}

			if (player.getEquipment().getItems()[equipDef.getType().getSlot()] != null && itemDef.isStackable()) {
				if (!player.getEquipment().contains(id)) {
					player.getInventory().set(slot,
							new Item(player.getEquipment().getItems()[equipDef.getType().getSlot()].getId(),
									player.getEquipment().getItems()[equipDef.getType().getSlot()].getAmount()));
				}
			} else if (player.getEquipment().getItems()[equipDef.getType().getSlot()] != null) {
				player.getInventory().set(slot,
						new Item(player.getEquipment().getItems()[equipDef.getType().getSlot()].getId(),
								player.getEquipment().getItems()[equipDef.getType().getSlot()].getAmount()));
			}

			if (itemDef.isStackable()) {
				if (player.getEquipment().contains(id)) {
					player.getEquipment().getItems()[equipDef.getType().getSlot()]
							.setAmount(player.getEquipment().getItems()[equipDef.getType().getSlot()].getAmount()
									+ stackableAmount);
				} else {
					player.getEquipment().set(equipDef.getType().getSlot(), new Item(id, stackableAmount));
				}

			} else {
				if (equipDef.isTwoHanded() && player.getEquipment().getItems()[Equipment.SHIELD] != null) {
					player.getInventory().add(new Item(player.getEquipment().getItems()[Equipment.SHIELD].getId(), 1));
					player.getEquipment().removeFromSlot(Equipment.SHIELD, 1);
					player.getEquipment().refresh();
				} else if (player.getEquipment().getItems()[Equipment.WEAPON] != null && EquipmentDefinition
						.get(player.getEquipment().getItems()[Equipment.WEAPON].getId()).isTwoHanded()
						&& equipDef.getType().getSlot() == Equipment.SHIELD) {
					player.getInventory().add(new Item(player.getEquipment().getItems()[Equipment.WEAPON].getId(), 1));
					player.getEquipment().removeFromSlot(Equipment.WEAPON, 1);
					player.getEquipment().refresh();
				}
				player.getEquipment().set(equipDef.getType().getSlot(), new Item(id, 1));
			}

			player.getEquipment().refresh();
			player.getEquipment().updateWeapon();
			player.getUpdateFlags().add(UpdateFlag.APPEARANCE);

		}

	}
}
