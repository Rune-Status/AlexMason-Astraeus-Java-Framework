package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.Sendable;

import java.util.Optional;

public final class SetWidgetAnimationPacket implements Sendable {

	private final int interfaceId;

	private final int animationId;

	public SetWidgetAnimationPacket(int interfaceId, int animationId) {
		this.interfaceId = interfaceId;
		this.animationId = animationId;
	}

	@Override
	public Optional<OutgoingPacket> writePacket(Player player) {
		GamePacketBuilder builder = new GamePacketBuilder(200);
		builder.writeShort(interfaceId)
		.writeShort(animationId);
		return builder.toOutgoingPacket();
	}

}
