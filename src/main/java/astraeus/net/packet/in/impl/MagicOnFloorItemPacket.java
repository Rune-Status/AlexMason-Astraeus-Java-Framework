package astraeus.net.packet.in.impl;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.in.IncomingPacketListener;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketReader;

/**
 * The {@link IncomingPacket} responsible for using magic on ground items.
 * 
 * @author SeVen
 */
@IncomingPacket.IncomingPacketOpcode(181)
public class MagicOnFloorItemPacket implements IncomingPacketListener {

	@SuppressWarnings("unused")
	@Override
	public void handlePacket(Player player, IncomingPacket packet) {
		GamePacketReader reader = packet.getReader();

		final int itemY = reader.readShort(ByteOrder.LITTLE);
		final int itemId = reader.readShort(false);
		final int itemX = reader.readShort(ByteOrder.LITTLE);
		final int spellId = reader.readShort(false, ByteModification.ADDITION);

	}

}
