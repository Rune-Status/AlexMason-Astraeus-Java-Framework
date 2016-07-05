package astraeus.net.packet.out;

import astraeus.game.model.Location;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.net.codec.ByteModification;
import astraeus.net.codec.game.GamePacketBuilder;
import astraeus.net.packet.OutgoingPacket;

public final class SetUpdateRegionPacket extends OutgoingPacket {

    private final Location position;

    public SetUpdateRegionPacket(Location position) {
	super(85);
	this.position = position;
    }

    @Override
    public GamePacketBuilder writePacket(Player player) {
	builder.write((position.getY() - 8 * player.getLastLocation().getRegionalY()), ByteModification.NEGATION)
	.write((position.getX() - 8 * player.getLastLocation().getRegionalX()), ByteModification.NEGATION);
	return builder;
    }

}
