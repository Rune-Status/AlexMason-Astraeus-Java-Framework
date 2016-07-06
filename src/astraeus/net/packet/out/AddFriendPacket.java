package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.Sendable;

import java.util.Optional;

public final class AddFriendPacket implements Sendable {

    private final long username;

    private int world;

    public AddFriendPacket(long username, int world) {
        this.username = username;
        this.world = world;
    }

    @Override
    public Optional<OutgoingPacket> writePacket(Player player) {
        GamePacketBuilder builder = new GamePacketBuilder(50);

        builder.writeLong(username);
        builder.write(world + 1);
        return builder.toOutgoingPacket();
    }

}
