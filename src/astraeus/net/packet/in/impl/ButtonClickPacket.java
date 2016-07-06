package astraeus.net.packet.in.impl;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.in.IncomingPacketListener;
import astraeus.plugins.clicking.ButtonClick;
import astraeus.net.codec.game.GamePacketReader;

/**
 * The {@link IncomingPacket} responsible for clicking buttons on the client.
 * 
 * @author SeVen
 */
@IncomingPacket.IncomingPacketOpcode(IncomingPacket.BUTTON_CLICK)
public class ButtonClickPacket implements IncomingPacketListener {

	@Override
	public void handlePacket(final Player player, IncomingPacket packet) {
		GamePacketReader reader = packet.getReader();
		
		final int buttonId = reader.readShort();

		new ButtonClick(player, buttonId).handleAction();
	}

}