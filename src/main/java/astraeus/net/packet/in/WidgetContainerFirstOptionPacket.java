package astraeus.net.packet.in;

import astraeus.game.model.entity.item.Item;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.mob.player.PlayerRights;
import astraeus.game.model.entity.mob.update.UpdateFlag;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.game.GamePacketReader;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.Receivable;
import astraeus.net.packet.IncomingPacket.IncomingPacketOpcode;
import astraeus.net.packet.out.ServerMessagePacket;

@IncomingPacketOpcode(IncomingPacket.WIDGET_CONTAINER_OPTION_1)
public final class WidgetContainerFirstOptionPacket implements Receivable {

	@Override
	public void handlePacket(Player player, IncomingPacket packet) {		
		final GamePacketReader reader = packet.getReader();
		
		final int interfaceId = reader.readShort(ByteModification.ADDITION);		
		final int itemSlot = reader.readShort(ByteModification.ADDITION);
		final int itemId = reader.readShort(ByteModification.ADDITION);

		if (player.getRights().greaterOrEqual(PlayerRights.DEVELOPER) && player.attr().get(Player.DEBUG_KEY)) {
			player.queuePacket(new ServerMessagePacket("[ItemContainerAction] - FirstAction - InterfaceId: " + interfaceId + " (" + itemId + ", " + itemSlot + ")"));
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

}
