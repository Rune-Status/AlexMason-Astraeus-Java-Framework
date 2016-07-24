package astraeus.net.packet.in;

import astraeus.game.event.impl.WidgetContainerFourthOptionEvent;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.game.GamePacketReader;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.Receivable;
import astraeus.net.packet.IncomingPacket.IncomingPacketOpcode;

@IncomingPacketOpcode(IncomingPacket.WIDGET_CONTAINER_OPTION_4)
public final class WidgetContainerFourthOptionPacket implements Receivable {

	@Override
	public void handlePacket(Player player, IncomingPacket packet) {
		final GamePacketReader reader = packet.getReader();
		final int itemSlot = reader.readShort(ByteModification.ADDITION);
		final int widgetId = reader.readShort();		
		final int itemId = reader.readShort(ByteModification.ADDITION);
		
		player.post(new WidgetContainerFourthOptionEvent(widgetId, itemId, itemSlot));		
	}

}
