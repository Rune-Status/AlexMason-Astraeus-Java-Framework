package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

public final class LogoutPlayerPacket extends OutgoingPacket {

    public LogoutPlayerPacket() {
	super(109);
    }

    @Override
    public GamePacketBuilder writePacket(Player player) {
	return builder;
    }

}
