package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

public final class SetWidgetTextPacket extends OutgoingPacket {

	private final int id;

	private final int level;

	private final int experience;

	public SetWidgetTextPacket(int id) {
		this(id, 99, 14_000_000);
	}

	public SetWidgetTextPacket(int id, int level, int experience) {
		super(134);
		this.id = id;
		this.level = level;
		this.experience = experience;
	}

	@Override
	public GamePacketBuilder writePacket(Player player) {
		synchronized (player) {
			builder.write(id);
			builder.writeInt(experience, ByteOrder.MIDDLE);
			builder.write(level);
			return builder;
		}
	}

}
