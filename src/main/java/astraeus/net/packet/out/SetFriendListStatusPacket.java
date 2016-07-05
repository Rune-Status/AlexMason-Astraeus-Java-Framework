package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.mob.player.PlayerRelation;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

/**
 * The packet that sends the first private messaging list load status.
 *
 *            The status for the friends lists are as follows:
 *            <p>
 *            <p>
 *            Loading: 0
 *            <p>
 *            Connecting: 1
 *            <p>
 *            Loaded: 2
 *            <p>
 *            <p>
 * @return an instance of this encoder.
 */
public final class SetFriendListStatusPacket extends OutgoingPacket {

	private final PlayerRelation.PrivateMessageListStatus status;

	public SetFriendListStatusPacket(PlayerRelation.PrivateMessageListStatus status) {
		super(221);
		this.status = status;
	}

	@Override
	public GamePacketBuilder writePacket(Player player) {
		builder.write(status.getCode());
		return builder;
	}

}
