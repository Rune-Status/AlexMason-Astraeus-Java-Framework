package astraeus.net.packet.in;

import astraeus.game.event.impl.ItemOnItemEvent;
import astraeus.game.model.entity.item.Item;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.game.GamePacketReader;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.Receivable;

@IncomingPacket.IncomingPacketOpcode(IncomingPacket.ITEM_ON_ITEM)
public final class ItemOnItemPacket implements Receivable {

	@Override
	public void handlePacket(Player player, IncomingPacket packet) {
		GamePacketReader reader = packet.getReader();
		
		final int usedWithSlot = reader.readShort();
		final int itemUsedSlot = reader.readShort(ByteModification.ADDITION);
		
		final Item used = player.getInventory().getItem(itemUsedSlot);
		
		final Item with = player.getInventory().getItem(usedWithSlot);		

		player.post(new ItemOnItemEvent(used, with));
	}

}

