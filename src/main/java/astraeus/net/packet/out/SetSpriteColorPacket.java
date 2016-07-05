package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

public final class SetSpriteColorPacket extends OutgoingPacket {

	private final int id;
	
	private final int color;
	
	public SetSpriteColorPacket(int id, int color) {
		super(203);
		this.id = id;
		this.color = color;
	}
	
	@Override
	public GamePacketBuilder writePacket(Player player) {
		builder.writeInt(id)
		.writeInt(color);
		return builder;
	}
	
}
