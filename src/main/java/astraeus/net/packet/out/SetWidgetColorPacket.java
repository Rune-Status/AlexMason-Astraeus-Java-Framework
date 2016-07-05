package astraeus.net.packet.out;

import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.ByteOrder;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

public final class SetWidgetColorPacket extends OutgoingPacket {

    private final int id;

    private final int color;

    public SetWidgetColorPacket(int id, int color) {
	super(122);
	this.id = id;
	this.color = color;
    }

    @Override
    public GamePacketBuilder writePacket(Player player) {
	builder.writeShort(id, ByteModification.ADDITION, ByteOrder.LITTLE)
	.writeShort(color, ByteModification.ADDITION, ByteOrder.LITTLE);
	return builder;
    }

}
