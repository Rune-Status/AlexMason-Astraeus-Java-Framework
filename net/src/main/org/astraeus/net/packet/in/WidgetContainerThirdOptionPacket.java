package astraeus.net.packet.in;

import astraeus.game.model.entity.item.Item;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.mob.player.PlayerRights;
import astraeus.game.model.entity.mob.player.attribute.Attribute;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketReader;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.Receivable;
import astraeus.net.packet.IncomingPacket.IncomingPacketOpcode;
import astraeus.net.packet.out.ServerMessagePacket;

@IncomingPacketOpcode(IncomingPacket.WIDGET_CONTAINER_OPTION_3)
public final class WidgetContainerThirdOptionPacket implements Receivable {

	@Override
	public void handlePacket(Player player, IncomingPacket packet) {
		final GamePacketReader reader = packet.getReader();
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

}
