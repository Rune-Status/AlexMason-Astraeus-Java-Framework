package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.Sendable;

import java.util.Optional;

public final class SetWidgetColorPacket implements Sendable {

    private final int id;

    private final int color;

    public SetWidgetColorPacket(int id, int color) {
        this.id = id;
        this.color = color;
    }

    @Override
    public Optional<OutgoingPacket> writePacket(Player player) {
        GamePacketBuilder builder = new GamePacketBuilder(122);
        builder.writeShort(id, ByteModification.ADDITION, ByteOrder.LITTLE)
                .writeShort(color, ByteModification.ADDITION, ByteOrder.LITTLE);
        return builder.toOutgoingPacket();
    }

}
