package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

public final class SetItemModelOnWidgetPacket extends OutgoingPacket {

    private final int id;

    private final int zoom;

    private final int model;

    public SetItemModelOnWidgetPacket(int id, int zoom, int model) {
	super(246);
	this.id = id;
	this.zoom = zoom;
	this.model = model;
    }

    @Override
    public GamePacketBuilder writePacket(Player player) {
	builder.writeShort(id, ByteOrder.LITTLE)
	.writeShort(zoom)
	.writeShort(model);
	return builder;
    }

}
