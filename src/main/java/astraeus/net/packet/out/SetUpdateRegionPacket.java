package astraeus.net.packet.out;

import astraeus.game.model.Position;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.Sendable;

import java.util.Optional;

public final class SetUpdateRegionPacket implements Sendable {

    private final Position position;

    public SetUpdateRegionPacket(Position position) {
        this.position = position;
    }

    @Override
    public Optional<OutgoingPacket> writePacket(Player player) {
        GamePacketBuilder builder = new GamePacketBuilder(85);
        builder.write((position.getY() - 8 * player.getLastLocation().getRegionalY()), ByteModification.NEGATION)
                .write((position.getX() - 8 * player.getLastLocation().getRegionalX()), ByteModification.NEGATION);
        return builder.toOutgoingPacket();
    }

}
