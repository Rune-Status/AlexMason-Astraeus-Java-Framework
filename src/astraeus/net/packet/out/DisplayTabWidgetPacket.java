package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.mob.update.UpdateFlag;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.Sendable;

import java.util.Optional;

public final class DisplayTabWidgetPacket implements Sendable {

	private final int id;

	public DisplayTabWidgetPacket(int id) {
		this.id = id;
	}

	@Override
	public Optional<OutgoingPacket> writePacket(Player player) {
		GamePacketBuilder builder = new GamePacketBuilder(106);
		builder.write(id, ByteModification.NEGATION);
		player.getUpdateFlags().add(UpdateFlag.APPEARANCE);
		return builder.toOutgoingPacket();
	}

}
