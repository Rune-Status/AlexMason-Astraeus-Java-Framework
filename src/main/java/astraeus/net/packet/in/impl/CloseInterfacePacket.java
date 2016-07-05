package astraeus.net.packet.in.impl;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.mob.player.PlayerRights;
import astraeus.game.model.entity.mob.player.attribute.Attribute;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.IncomingPacket.IncomingPacketOpcode;
import astraeus.net.packet.in.IncomingPacketListener;
import astraeus.net.packet.out.ServerMessagePacket;

/**
 * The {@link IncomingPacket} responsible for closing interfaces.
 * 
 * @author SeVen
 */
@IncomingPacketOpcode(IncomingPacket.CLOSE_WINDOW)
public class CloseInterfacePacket implements IncomingPacketListener {

	@Override
	public void handlePacket(Player player, IncomingPacket packet) {

		switch (packet.getOpcode()) {

			case IncomingPacket.CLOSE_WINDOW:
				if ((Boolean) player.attr().get(Attribute.BANKING)) {
					player.attr().put(Attribute.BANKING, false);
				}

				if ((Boolean) player.attr().get(Attribute.SHOPPING)) {
					player.attr().put(Attribute.SHOPPING, false);
				}

				if (player.getRights().equals(PlayerRights.DEVELOPER) && player.attr().contains(Attribute.DEBUG, true)) {
					player.send(new ServerMessagePacket("[CloseInterface] - Closed Window"));
				}

				break;
		}
	}
}