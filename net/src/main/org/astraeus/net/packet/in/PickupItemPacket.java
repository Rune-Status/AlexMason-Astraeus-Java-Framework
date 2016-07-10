package astraeus.net.packet.in;

import astraeus.game.model.Location;
import astraeus.game.model.entity.item.Item;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.mob.player.PlayerRights;
import astraeus.game.model.entity.mob.player.attribute.Attribute;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.Receivable;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketReader;
import astraeus.net.packet.out.ServerMessagePacket;

/**
 * The {@link IncomingPacket} responsible for picking up an item on the ground.
 * 
 * @author SeVen
 */
@IncomingPacket.IncomingPacketOpcode(IncomingPacket.PICKUP_GROUND_ITEM)
public class PickupItemPacket implements Receivable {

	@Override
	public void handlePacket(final Player player, IncomingPacket packet) {
		GamePacketReader reader = packet.getReader();
		
		final int y = reader.readShort(ByteOrder.LITTLE);
		final int id = reader.readShort(false);
		final int x = reader.readShort(ByteOrder.LITTLE);

		Item item = new Item(id);

		Location location = new Location(x, y, player.getLocation().getHeight());

		if (player.getRights().equals(PlayerRights.DEVELOPER) && player.attr().contains(Attribute.DEBUG, true)) {
			player.send(new ServerMessagePacket(String.format("[PickupItem] - Item: %s Location: %s", item.toString(), location.toString())));
		}

		if (Math.abs(player.getLocation().getX() - x) > 25 || Math.abs(player.getLocation().getY() - y) > 25) {
			player.getMovement().reset();
			return;
		}

	}
}
