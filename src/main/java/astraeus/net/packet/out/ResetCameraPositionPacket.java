package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.Sendable;

import java.util.Optional;

public final class ResetCameraPositionPacket implements Sendable {

    @Override
    public Optional<OutgoingPacket> writePacket(Player player) {
	    return new GamePacketBuilder(107).toOutgoingPacket();
    }

}
