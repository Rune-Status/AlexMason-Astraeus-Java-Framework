package astraeus.net.packet.in;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.mob.player.PlayerRights;
import astraeus.game.model.entity.mob.player.attribute.Attribute;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.game.GamePacketReader;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.Receivable;
import astraeus.net.packet.out.ServerMessagePacket;

/**
 * The {@link IncomingPacket} responsible for wielding items.
 * 
 * @author SeVen
 */
@IncomingPacket.IncomingPacketOpcode(41)
public class WieldItemPacket implements Receivable {

	@Override
	public void handlePacket(Player player, IncomingPacket packet) {
		GamePacketReader reader = packet.getReader();

		final int id = reader.readShort();		
		final int slot = reader.readShort(ByteModification.ADDITION);		
		final int interfaceId = reader.readShort(ByteModification.ADDITION);
		
		if (player.getRights().greaterOrEqual(PlayerRights.DEVELOPER) && player.attr().contains(Attribute.DEBUG, true)) {
			player.send(new ServerMessagePacket(String.format("[WearItem] - [id= %d], [slot= %d], [interfaceId= %d]", id, slot, interfaceId)));
		}
		
	}
}
