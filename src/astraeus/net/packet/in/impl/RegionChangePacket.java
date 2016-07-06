package astraeus.net.packet.in.impl;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.mob.player.attribute.Attribute;
import astraeus.game.model.entity.object.GameObjects;
import astraeus.game.model.entity.object.impl.DoorUtils;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.in.IncomingPacketListener;

/**
 * The {@link IncomingPacket}'s responsible for changing a players region. Used
 * when a player enters a new map region or when the map region has been
 * successfully loaded.
 *
 * @author SeVen
 */
@IncomingPacket.IncomingPacketOpcode({IncomingPacket.LOADED_REGION, IncomingPacket.ENTER_REGION})
public class RegionChangePacket implements IncomingPacketListener {

	@Override
	public void handlePacket(Player player, IncomingPacket packet) {
		player.attr().put(Attribute.SAVE, true);

		switch (packet.getOpcode()) {

			case IncomingPacket.ENTER_REGION:
				break;

			case IncomingPacket.LOADED_REGION:
				GameObjects.createGlobalObjects(player);
				DoorUtils.createDoors(player);
				break;
		}
	}
}