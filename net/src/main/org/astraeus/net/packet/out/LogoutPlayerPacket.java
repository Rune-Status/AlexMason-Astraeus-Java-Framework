package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.Sendable;

import java.util.Optional;

public final class LogoutPlayerPacket implements Sendable {

    @Override
    public Optional<OutgoingPacket> writePacket(Player player) {
        GamePacketBuilder builder = new GamePacketBuilder(109);
        return builder.toOutgoingPacket();
    }

}
