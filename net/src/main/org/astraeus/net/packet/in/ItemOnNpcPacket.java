package astraeus.net.packet.in;

import astraeus.game.model.World;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketReader;
import astraeus.net.packet.IncomingPacket;
import astraeus.net.packet.Receivable;

@IncomingPacket.IncomingPacketOpcode(IncomingPacket.ITEM_ON_NPC)
public final class ItemOnNpcPacket implements Receivable {

	@Override
	public void handlePacket(Player player, IncomingPacket packet) {
		GamePacketReader reader = packet.getReader();
		
		@SuppressWarnings("unused")
		final int itemId = reader.readShort(false, ByteModification.ADDITION);
		final int npcSlot = reader.readShort(false, ByteModification.ADDITION);
		final int itemSlot = reader.readShort(ByteOrder.LITTLE);

		@SuppressWarnings("unused")
		final int npcId = World.WORLD.getMobs()[npcSlot].getId();

		if (!player.getInventory().contains(itemSlot)) {
			return;
		}

		if (World.WORLD.getMobs()[npcSlot] == null) {
			return;
		}

		//new ItemOnNpc(player, player.getInventory().getItem(itemSlot), new Npc(npcId, World.getMobs()[npcSlot].getSlot())).handleAction();
	}

}

