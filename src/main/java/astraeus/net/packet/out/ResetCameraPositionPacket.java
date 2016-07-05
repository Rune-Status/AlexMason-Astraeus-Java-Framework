package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

public final class ResetCameraPositionPacket extends OutgoingPacket {

    public ResetCameraPositionPacket() {
	super(107);
    }

    @Override
    public GamePacketBuilder writePacket(Player player) {
	return builder;
    }

    @Override
    public int getOpcode() {
	return 107;
    }

}
