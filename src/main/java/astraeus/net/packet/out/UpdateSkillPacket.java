package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.Sendable;

import java.util.Optional;

public final class UpdateSkillPacket implements Sendable {

	private final int id;

	private final int level;

	private final int experience;

	public UpdateSkillPacket(int id) {
		this(id, 99, 14_000_000);
	}

	public UpdateSkillPacket(int id, int level, int experience) {
		this.id = id;
		this.level = level;
		this.experience = experience;
	}

	@Override
	public Optional<OutgoingPacket> writePacket(Player player) {
			GamePacketBuilder builder = new GamePacketBuilder(134);
			builder.write(id);
			builder.writeInt(experience, ByteOrder.MIDDLE);
			builder.write(level);
			return builder.toOutgoingPacket();
	}

}
