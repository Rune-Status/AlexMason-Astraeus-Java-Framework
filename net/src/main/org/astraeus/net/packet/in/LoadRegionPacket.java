package astraeus.net.packet.in;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.mob.player.attribute.Attribute;
import astraeus.game.model.entity.object.GameObjects;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.Receivable;

@IncomingPacket.IncomingPacketOpcode(IncomingPacket.LOADED_REGION)
public final class LoadRegionPacket implements Receivable {

	@Override
	public void handlePacket(Player player, IncomingPacket packet) {
		player.attr().put(Attribute.SAVE, true);
		
		GameObjects.createGlobalObjects(player);
	}

}
