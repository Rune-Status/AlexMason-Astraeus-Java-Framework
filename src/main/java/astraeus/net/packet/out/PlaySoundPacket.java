package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.mob.player.attribute.Attribute;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

public final class PlaySoundPacket extends OutgoingPacket {

	private final int id;

	private final int volume;

	private final int delay;

	public PlaySoundPacket(int id) {
		this(id, 50, 0);
	}

	public PlaySoundPacket(int id, int volume) {
		this(id, volume, 0);
	}

	public PlaySoundPacket(int id, int volume, int delay) {
		super(174);
		this.id = id;
		this.volume = volume;
		this.delay = delay;
	}

	@Override
	public GamePacketBuilder writePacket(Player player) {
		if (!(Boolean) player.attr().get(Attribute.SOUND)) {
			return builder;
		}
		if (id > 0) {
			builder.writeShort(id)
			.write(volume)
			.writeShort(delay);

		}
		return builder;
	}

}
