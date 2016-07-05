package astraeus.net.packet.out;

import astraeus.game.model.entity.item.Item;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.PacketHeader;

public final class UpdateItemsOnWidgetPacket extends OutgoingPacket {

	private final int id;

	private final Item[] items;

	public UpdateItemsOnWidgetPacket(int id, Item... items) {
		super(53, PacketHeader.VARIABLE_SHORT);
		this.id = id;
		this.items = items;
	}

	@Override
	public GamePacketBuilder writePacket(Player player) {
		builder.writeShort(id).writeShort(items.length);
		for (final Item item : items) {
			if (item != null) {
				if (item.getAmount() > 254) {
					builder.write(255).writeInt(item.getAmount(), ByteOrder.INVERSE_MIDDLE);
				} else {
					builder.write(item.getAmount());
				}
				builder.writeShort(item.getId() + 1, ByteModification.ADDITION, ByteOrder.LITTLE);
			} else {
				builder.write(0).writeShort(0, ByteModification.ADDITION, ByteOrder.LITTLE);
			}
		}
		return builder;
	}

}
