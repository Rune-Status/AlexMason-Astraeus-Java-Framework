package astraeus.net.packet.out;

import astraeus.game.model.entity.item.Item;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.PacketHeader;
import astraeus.net.packet.Sendable;

import java.util.Optional;

public final class SetItemOnInterfaceSlotPacket implements Sendable {

	private final int interfaceId;

	private final Item item;

	private final int slot;

	public SetItemOnInterfaceSlotPacket(int interfaceId, Item item, int slot) {
		this.interfaceId = interfaceId;
		this.item = item;
		this.slot = slot;
	}

	@Override
	public Optional<OutgoingPacket> writePacket(Player player) {
		GamePacketBuilder builder = new GamePacketBuilder(34, PacketHeader.VARIABLE_SHORT);
		builder.writeShort(interfaceId).write(slot);
		if (item != null) {
			builder.writeShort(item.getId() + 1).write(255).writeInt(item.getAmount());
		} else {
			builder.writeShort(0).write(255).write(0);
		}
		return builder.toOutgoingPacket();
	}

}
