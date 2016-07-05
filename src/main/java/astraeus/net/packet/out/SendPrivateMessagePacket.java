package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.mob.player.PlayerRights;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.PacketHeader;
import astraeus.net.packet.Sendable;

import java.util.Optional;

public final class SendPrivateMessagePacket implements Sendable {

    private final long name;

    private final PlayerRights rights;

    private final byte[] message;

    private final int size;

    public SendPrivateMessagePacket(long name, PlayerRights rights, byte[] message, int size) {
        this.name = name;
        this.rights = rights;
        this.message = message;
        this.size = size;
    }

    @Override
    public Optional<OutgoingPacket> writePacket(Player player) {
        GamePacketBuilder builder = new GamePacketBuilder(196, PacketHeader.VARIABLE_BYTE);
        builder.writeLong(name)
                .writeInt(player.lastMessage++)
                .write(rights.getProtocolValue())
                .writeBytes(message, size);
        return builder.toOutgoingPacket();
    }

}
