package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;
import astraeus.net.packet.Sendable;

import java.util.Optional;

public final class SetItemModelOnWidgetPacket implements Sendable {

    private final int id;

    private final int zoom;

    private final int model;

    public SetItemModelOnWidgetPacket(int id, int zoom, int model) {
        this.id = id;
        this.zoom = zoom;
        this.model = model;
    }

    @Override
    public Optional<OutgoingPacket> writePacket(Player player) {
        GamePacketBuilder builder = new GamePacketBuilder(246);
        builder.writeShort(id, ByteOrder.LITTLE)
                .writeShort(zoom)
                .writeShort(model);
        return builder.toOutgoingPacket();
    }

}
