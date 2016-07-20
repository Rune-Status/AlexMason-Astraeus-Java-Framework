package astraeus.net.packet.in;

import astraeus.game.event.impl.ItemFirstClickEvent;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketReader;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.Receivable;

@IncomingPacket.IncomingPacketOpcode(IncomingPacket.ITEM_OPTION_1)
public final class ItemFirstOptionPacket implements Receivable {

	@Override
	public void handlePacket(Player player, IncomingPacket packet) {
		final GamePacketReader reader = packet.getReader();
		final int widgetId = reader.readShort(ByteOrder.LITTLE, ByteModification.ADDITION);
		final int slot = reader.readShort(false, ByteModification.ADDITION);
		final int id = reader.readShort(ByteOrder.LITTLE);

		player.post(new ItemFirstClickEvent(id, slot, widgetId));
	}

}
