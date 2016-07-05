package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

public final class SetWidgetAnimationPacket extends OutgoingPacket {

	private final int interfaceId;

	private final int animationId;

	public SetWidgetAnimationPacket(int interfaceId, int animationId) {
		super(200);
		this.interfaceId = interfaceId;
		this.animationId = animationId;
	}

	@Override
	public GamePacketBuilder writePacket(Player player) {
		builder.writeShort(interfaceId)
		.writeShort(animationId);
		return builder;
	}

}
