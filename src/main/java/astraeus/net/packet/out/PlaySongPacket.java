package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.mob.player.attribute.Attribute;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

/**
 * The {@link OutgoingPacket} that plays a song.
 * 
 * @author SeVen
 */
public final class PlaySongPacket extends OutgoingPacket {

	/**
	 * The id of the song.
	 */
	private final int id;

	/**
	 * Creates a new {@link PlaySongPacket}.
	 * 
	 * @param id
	 *            The id of the song.
	 */
	public PlaySongPacket(int id) {
		super(74);
		this.id = id;
	}

	@Override
	public GamePacketBuilder writePacket(Player player) {
		if (!(Boolean) player.attr().get(Attribute.MUSIC) || id == -1) {
			return builder;
		}
		builder.writeShort(id, ByteOrder.LITTLE);
		return builder;
	}

}
