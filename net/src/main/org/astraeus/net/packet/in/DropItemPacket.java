package astraeus.net.packet.in;

import astraeus.game.model.entity.item.Item;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.mob.player.PlayerRights;
import astraeus.game.model.entity.mob.player.attribute.Attribute;
import astraeus.net.codec.ByteModification;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.Receivable;
import astraeus.net.packet.IncomingPacket.IncomingPacketOpcode;
import astraeus.net.codec.game.GamePacketReader;
import astraeus.net.packet.out.AddGroundItemPacket;
import astraeus.net.packet.out.ServerMessagePacket;

/**
 * The {@link IncomingPacket} responsible for dropping items.
 * 
 * @author SeVen
 */
@IncomingPacketOpcode(IncomingPacket.DROP_ITEM)
public final class DropItemPacket implements Receivable {

	@Override
	public void handlePacket(Player player, IncomingPacket packet) {
		
		System.out.println("drop item packet");
		
		GamePacketReader reader = packet.getReader();
		
		final int itemId = reader.readShort(false, ByteModification.ADDITION);
		
		reader.readByte(false);
		reader.readByte(false);

        final int slot = reader.readShort(false, ByteModification.ADDITION);
        
        System.out.println("dropping item: " + itemId + " slot: " + slot);
        
        final Item item = player.getInventory().getItem(slot);
        
        if (item == null) {
        	return;
        }       

        //TODO add destoryable items
		boolean droppable = true;

		if (!droppable) {
			player.send(new ServerMessagePacket("This item cannot be dropped."));
			return;
		}

		if (player.getRights().equals(PlayerRights.DEVELOPER) && player.attr().contains(Attribute.DEBUG, true)) {
			player.send(new ServerMessagePacket("ItemDropped: " + itemId));
		}
		
		player.send(new AddGroundItemPacket(item));
		
		player.getInventory().remove(item);	
		
	}

}
