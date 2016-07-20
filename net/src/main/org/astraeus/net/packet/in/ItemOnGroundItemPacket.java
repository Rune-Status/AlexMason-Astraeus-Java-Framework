package astraeus.net.packet.in;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.mob.player.PlayerRights;
import astraeus.game.model.entity.mob.player.attribute.Attribute;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketReader;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.Receivable;
import astraeus.net.packet.out.ServerMessagePacket;

@IncomingPacket.IncomingPacketOpcode(IncomingPacket.ITEM_ON_GROUND_ITEM)
public final class ItemOnGroundItemPacket implements Receivable {

	@Override
	public void handlePacket(Player player, IncomingPacket packet) {
		GamePacketReader reader = packet.getReader();

		final int a1 = reader.readShort(ByteOrder.LITTLE);
		final int itemUsed = reader.readShort(false, ByteModification.ADDITION);
		final int groundItem = reader.readShort();
		final int gItemY = reader.readShort(false, ByteModification.ADDITION);
		final int itemUsedSlot = reader.readShort(ByteOrder.LITTLE, ByteModification.ADDITION);
		final int gItemX = reader.readShort();

		if (player.getRights().equal(PlayerRights.DEVELOPER) && player.attr().contains(Attribute.DEBUG, true)) {
			player.send(new ServerMessagePacket("ItemUsed: " + itemUsed + " groundItem: " + groundItem + " itemUsedSlot: " + itemUsedSlot + " gItemX: " + gItemX + " gItemY: " + gItemY + " a1: " + a1));
		}
	}

}
