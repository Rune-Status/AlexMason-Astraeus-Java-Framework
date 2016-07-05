package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

/**
 * The {@link OutgoingPacket} that changes the state of the minimap.

 *            The state of the minimap.
 *            <p>
 *            Normal: {@code 0}
 *            <p>
 * 
 *            Normal, but unclickable: {@code 1}
 *            <p>
 * 
 *            Hidden (Black): {@code 2}
 *            <p>
 * 
 * @author SeVen
 */
public final class SetMinimapStatePacket extends OutgoingPacket {

	private final int state;

	public SetMinimapStatePacket(int state) {
		super(99);
		this.state = state;
	}

	@Override
	public GamePacketBuilder writePacket(Player player) {
		builder.write(state);
		return builder;
	}

}
